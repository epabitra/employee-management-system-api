package com.nihar.service;

import org.springframework.stereotype.Service;


import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.entity.User;

@Service
public interface UserService {
    User addUser(UserDetailsDTO dto);
    AdminDashboardCounterDTO getAdminDashboardCounter();
	Object getAllUsers();
}
