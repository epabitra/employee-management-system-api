package com.nihar.service.impl;

import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.entity.User;
import com.nihar.repository.UserRepository;
import com.nihar.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Save a new user
    @Override
    public User addUser(UserDetailsDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPasswordHash());
        user.setImageUrl(dto.getImageUrl());
        user.setLangKey(dto.getLangKey());
        user.setTimezoneId(dto.getTimezoneId());
        user.setMobileNumber(dto.getMobileNumber());
        user.setGender(dto.getGender());
        user.setDepartment(dto.getDepartment());
        user.setDesignation(dto.getDesignation());
        user.setRole(dto.getRole());
        user.setSalary(dto.getSalary());

        // Convert java.util.Date to java.time.LocalDate
        if (dto.getJoiningDate() != null) {
            user.setJoiningDate(dto.getJoiningDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());
        }

        if (dto.getDateOfBirth() != null) {
            user.setDateOfBirth(dto.getDateOfBirth().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());
        }

        return userRepository.save(user);
    }

    // Return total users for dashboard
    @Override
    public AdminDashboardCounterDTO getAdminDashboardCounter() {
        AdminDashboardCounterDTO counter = new AdminDashboardCounterDTO();
        counter.setEmployeeCount(userRepository.count());

        // You can enhance this logic if attendance tracking exists
        counter.setPresentEmployeeCount(0);
        counter.setAbsentEmployeeCount(0);
        counter.setSalaryProgress(0.0);

        return counter;
    }

    // Optional: Convert User list to DTOs
    public List<UserDetailsDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserDetailsDTO dto = new UserDetailsDTO();
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setMobileNumber(user.getMobileNumber());
            dto.setGender(user.getGender());
            dto.setDepartment(user.getDepartment());
            dto.setDesignation(user.getDesignation());
            dto.setRole(user.getRole());
            dto.setSalary(user.getSalary());
            // You can reverse-convert dates too if needed
            return dto;
        }).collect(Collectors.toList());
    }
}
