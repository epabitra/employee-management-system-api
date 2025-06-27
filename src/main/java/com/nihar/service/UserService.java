package com.nihar.service;

import org.springframework.stereotype.Service;


import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.CountEmployeeDTO;
import com.nihar.dto.FullUserDetailsDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.dto.UserWithRoleDTO;
import com.nihar.entity.User;

import java.util.Optional;

@Service
public interface UserService {
    User addUser(UserDetailsDTO dto);
    AdminDashboardCounterDTO getAdminDashboardCounter();
    FullUserDetailsDTO getFullUserDetails(String email);

	Object getAllUsers();
    Optional<User> loginUser(String email, String password);
    
    CountEmployeeDTO getEmployeeCount();//to count the employees active
	UserWithRoleDTO getUserWithRoles(String email);
}
