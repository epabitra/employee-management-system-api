package com.nihar.service;

import com.nihar.dto.*;
import com.nihar.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User addUser(UserDetailsDTO dto);
    AdminDashboardCounterDTO getAdminDashboardCounter();
    FullUserDetailsDTO getFullUserDetails(String email);
    Object getAllUsers();
    Optional<User> loginUser(String email, String password);
    CountEmployeeDTO getEmployeeCount(); // to count the employees active
    UserWithRoleDTO getUserWithRoles(String email);

    // ✅ Add this method to assign role & department to user
    void assignRoleAndDepartment(Long userId, String roleUuid, String departmentUuid);

}
