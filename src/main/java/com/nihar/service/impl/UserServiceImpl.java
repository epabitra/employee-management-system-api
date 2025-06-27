package com.nihar.service.impl;

import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.CountEmployeeDTO;
import com.nihar.dto.FullUserDetailsDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.dto.UserWithRoleDTO;
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
            user.setJoiningDate(dto.getJoiningDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());
        }

        if (dto.getDateOfBirth() != null) {
            user.setDateOfBirth(dto.getDateOfBirth().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());
        }

        userRepository.save(user);

        Role role = roleService.findByUuid(dto.getRoleUuid())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Department department = departmentService.findByUuid(dto.getDepartmentUuid())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        UserRoleDepartment urd = new UserRoleDepartment();
        urd.setUuid(UUID.randomUUID().toString());
        urd.setUser(user);
        urd.setRole(role);
        urd.setDepartment(department);
        urd.setCreatedDate(LocalDateTime.now());
        urd.setActive(true);
        urd.setCreatedBy(user.getEmail());
        urd.setUpdatedBy(user.getEmail());

        userRoleDepartmentService.save(urd);

        return user;
    }

    @Override
    public Optional<User> loginUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPasswordHash()));
    }

    @Override
    public AdminDashboardCounterDTO getAdminDashboardCounter() {
        AdminDashboardCounterDTO counter = new AdminDashboardCounterDTO();
        counter.setEmployeeCount(userRepository.count());
        counter.setPresentEmployeeCount(0); // Placeholder
        counter.setAbsentEmployeeCount(0);  // Placeholder
        counter.setSalaryProgress(0.0);     // Placeholder
        return counter;
    }

    @Override
    public List<UserDetailsDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            List<String> roles = user.getUserRoleDepartment().stream()
                    .map(urd -> urd.getRole().getName())
                    .toList();

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

        // ✅ Set roles
        dto.setRoles(user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .filter(Objects::nonNull)
                .distinct()
                .toList());

        // ✅ Set departments
        dto.setDepartments(user.getUserRoleDepartment().stream()
                .map(urd -> urd.getDepartment().getName())
                .filter(Objects::nonNull)
                .distinct()
                .toList());

        // ✅ Set authorities (ROLE_ prefix required by Spring Security)
        Set<String> authorities = user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .filter(Objects::nonNull)
                .map(roleName -> "ROLE_" + roleName.toUpperCase())
                .collect(Collectors.toSet());

        dto.setAuthorities(authorities);

        return dto;
    }

    @Override
    public CountEmployeeDTO getEmployeeCount() {
        long count = userRepository.countActiveEmployees();
        return new CountEmployeeDTO(count);
    }

    // ✅ Optional: Method to retrieve user + roles for UI
    public UserWithRoleDTO getUserWithRoles(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .toList();

        return UserWithRoleDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }
}
