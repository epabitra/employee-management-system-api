package com.nihar.service.impl;

import com.nihar.dto.*;
import com.nihar.entity.*;
import com.nihar.repository.UserRepository;
import com.nihar.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final DepartmentService departmentService;
    private final UserRoleDepartmentService userRoleDepartmentService;

    @Override
    public User addUser(UserDetailsDTO dto) {
        if (dto.getRoleUuid() == null || dto.getDepartmentUuid() == null) {
            throw new IllegalArgumentException("Role UUID and Department UUID must not be null");
        }

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        user.setImageUrl(dto.getImageUrl());
        user.setLangKey(dto.getLangKey());
        user.setTimezoneId(dto.getTimezoneId());
        user.setMobileNumber(dto.getMobileNumber());
        user.setGender(dto.getGender());
        user.setDesignation(dto.getDesignation());
        user.setSalary(dto.getSalary());
        user.setCreatedDate(LocalDateTime.now());
        user.setActive(true);

        if (dto.getJoiningDate() != null) {
            user.setJoiningDate(dto.getJoiningDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        if (dto.getDateOfBirth() != null) {
            user.setDateOfBirth(dto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        userRepository.save(user);

        Role role = roleService.findByUuid(dto.getRoleUuid())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Department department = departmentService.findByUuid(dto.getDepartmentUuid())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        saveUserRoleDepartment(user, role, department);

        return user;
    }

    @Override
    public Optional<User> loginUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPasswordHash()));
    }

    @Override
    public AdminDashboardCounterDTO getAdminDashboardCounter() {
        AdminDashboardCounterDTO dto = new AdminDashboardCounterDTO();
        dto.setEmployeeCount(userRepository.count());
        dto.setPresentEmployeeCount(0);  // TODO: Add actual logic
        dto.setAbsentEmployeeCount(0);   // TODO: Add actual logic
        dto.setSalaryProgress(0.0);      // TODO: Add actual logic
        return dto;
    }

    @Override
    public List<UserDetailsDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            List<String> roles = user.getUserRoleDepartment().stream()
                    .map(urd -> urd.getRole().getName())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            UserDetailsDTO dto = new UserDetailsDTO();
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setMobileNumber(user.getMobileNumber());
            dto.setGender(user.getGender());
            dto.setDesignation(user.getDesignation());
            dto.setRoles(roles);
            dto.setSalary(user.getSalary());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public FullUserDetailsDTO getFullUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FullUserDetailsDTO dto = new FullUserDetailsDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setGender(user.getGender());
        dto.setDesignation(user.getDesignation());
        dto.setSalary(user.getSalary());
        dto.setJoiningDate(user.getJoiningDate());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setImageUrl(user.getImageUrl());
        dto.setTimezoneId(user.getTimezoneId());
        dto.setLangKey(user.getLangKey());

        dto.setRoles(user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList()));

        dto.setDepartments(user.getUserRoleDepartment().stream()
                .map(urd -> urd.getDepartment().getName())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList()));

        dto.setAuthorities(user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .filter(Objects::nonNull)
                .map(roleName -> "ROLE_" + roleName.toUpperCase())
                .collect(Collectors.toSet()));

        return dto;
    }

    @Override
    public CountEmployeeDTO getEmployeeCount() {
        long count = userRepository.countActiveEmployees();
        return new CountEmployeeDTO(count);
    }

    @Override
    public UserWithRoleDTO getUserWithRoles(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        UserWithRoleDTO dto = new UserWithRoleDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRoles(roles);
        return dto;
    }

    @Override
    public void assignRoleAndDepartment(Long userId, String roleUuid, String departmentUuid) {
        if (roleUuid == null || departmentUuid == null) {
            throw new IllegalArgumentException("Role UUID and Department UUID must not be null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleService.findByUuid(roleUuid)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Department department = departmentService.findByUuid(departmentUuid)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        UserRoleDepartment urd = new UserRoleDepartment();
        urd.setUuid(UUID.randomUUID().toString());
        urd.setUser(user);
        urd.setRole(role);
        urd.setDepartment(department);
        urd.setCreatedDate(LocalDateTime.now());
        urd.setUpdatedDate(LocalDateTime.now());
        urd.setCreatedBy(user.getEmail());
        urd.setUpdatedBy(user.getEmail());
        urd.setActive(true);

        userRoleDepartmentService.save(urd);
    }


    private void saveUserRoleDepartment(User user, Role role, Department department) {
        UserRoleDepartment urd = new UserRoleDepartment();
        urd.setUuid(UUID.randomUUID().toString());
        urd.setUser(user);
        urd.setRole(role);
        urd.setDepartment(department);
        urd.setCreatedDate(LocalDateTime.now());
        urd.setUpdatedDate(LocalDateTime.now());
        urd.setCreatedBy(user.getEmail());
        urd.setUpdatedBy(user.getEmail());
        urd.setActive(true);

        userRoleDepartmentService.save(urd);
    }
}
