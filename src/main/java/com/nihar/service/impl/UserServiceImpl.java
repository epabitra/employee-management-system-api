package com.nihar.service.impl;

import com.nihar.dto.*;
import com.nihar.entity.*;
import com.nihar.repository.DepartmentRepository;
import com.nihar.repository.SalaryRepository;
import com.nihar.repository.TeamRepository;
import com.nihar.repository.UserRepository;
import com.nihar.repository.UserRoleDepartmentRepository;
import com.nihar.repository.UserTeamRepository;
import com.nihar.service.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SalaryRepository salaryRepository;
    private final DepartmentRepository departmentRepository;
    
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final DepartmentService departmentService;
    private final UserRoleDepartmentService userRoleDepartmentService;
    private final UserRoleDepartmentRepository userRoleDepartmentRepository;
    private final MailService mailService;
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private UserTeamRepository userTeamRepository;



    @Override
    @Transactional
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

        Role role = null;
        if (dto.getRoleUuid() != null && !"null".equals(dto.getRoleUuid().toLowerCase())) {
            role = roleService.findByUuid(dto.getRoleUuid())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        }

        Department department = null;
        if (dto.getDepartmentUuid() != null && !dto.getDepartmentUuid().isEmpty()) {
            department = departmentService.findByUuid(dto.getDepartmentUuid())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }

        if (role != null || department != null) {
            saveUserRoleDepartment(user, role, department);
        }

        if (dto.getTeamUuid() != null && !dto.getTeamUuid().isEmpty()) {
            Team team = teamService.findByUuid(dto.getTeamUuid())
                    .orElseThrow(() -> new RuntimeException("Team not found"));

            UserTeam userTeam = UserTeam.builder()
                    .uuid(UUID.randomUUID().toString())
                    .user(user)
                    .team(team)
                    .active(true)
                    .createdDate(LocalDateTime.now())
                    .build();

            userTeamRepository.save(userTeam);
        }

     // ✅ Send welcome email if requested
        if (dto.isSendEmail() && dto.getEmail() != null) {
            String to = dto.getEmail();
            String subject = "Welcome to the Company";
            String body = "Hello " + dto.getFirstName() + ",\n\nWelcome to our team! We're excited to have you on board.";

            mailService.sendWelcomeEmail(to, subject, body);
        }


        return user;
    }




    private void saveUserRoleDepartment(User user, Role role, Department department) {
        UserRoleDepartment urd = new UserRoleDepartment();
        urd.setUser(user);
        urd.setRole(role); // can be null
        urd.setDepartment(department); // can be null
        urd.setUuid(UUID.randomUUID().toString());
        urd.setCreatedDate(LocalDateTime.now());
        urd.setActive(true);
        userRoleDepartmentRepository.save(urd);
    }



    @Override
    public Optional<User> loginUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPasswordHash()));
    }

    @Override
    public AdminDashboardCounterDTO getAdminDashboardCounter() {
        long totalEmployees = userRepository.count();
        long totalTeams = teamRepository.count();
        double totalSalary = salaryRepository.sumAllSalaries(); 
        long totalDepartments = departmentRepository.count();

        return AdminDashboardCounterDTO.builder()
                .employeeCount(totalEmployees)
                .totalSalary(totalSalary)
                .totalTeams(totalTeams)
                .totalDepartments(totalDepartments)
                .build();

    }
    
    
    @Override
    public List<String> getUsersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        List<User> users = userRepository.findUsersWithBirthdayToday(today.getMonthValue(), today.getDayOfMonth());

        return users.stream()
                    .map(user -> user.getFirstName() + " " + user.getLastName())
                    .toList();
    }

    
    @Override
    public List<DepartmentEmployeeCountDTO> getEmployeeCountGroupedByDepartment() {
        return userRepository.getEmployeeCountByDepartment();
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
        // Skip entirely if both are null
        if (roleUuid == null && departmentUuid == null) {
            return;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = null;
        if (roleUuid != null) {
            role = roleService.findByUuid(roleUuid)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        }

        Department department = null;
        if (departmentUuid != null) {
            department = departmentService.findByUuid(departmentUuid)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }

        UserRoleDepartment urd = new UserRoleDepartment();
        urd.setUuid(UUID.randomUUID().toString());
        urd.setUser(user);
        urd.setRole(role);                   // will be null if roleUuid is null
        urd.setDepartment(department);       // will be null if departmentUuid is null
        urd.setCreatedDate(LocalDateTime.now());
        urd.setUpdatedDate(LocalDateTime.now());
        urd.setCreatedBy(user.getEmail());
        urd.setUpdatedBy(user.getEmail());
        urd.setActive(true);

        userRoleDepartmentService.save(urd);
    }
    
    
    


    
}
