package com.nihar.service;

import com.nihar.dto.*;
import com.nihar.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User addUser(UserDetailsDTO dto);
    AdminDashboardCounterDTO getAdminDashboardCounter();
    FullUserDetailsDTO getFullUserDetails(String email);
    Object getAllUsers();
    Optional<User> loginUser(String email, String password);
   
    UserWithRoleDTO getUserWithRoles(String email);

    //  method to assign role & department to user
    void assignRoleAndDepartment(Long userId, String roleUuid, String departmentUuid);
    
    List<DepartmentEmployeeCountDTO> getEmployeeCountGroupedByDepartment();
    
    List<String> getUsersWithBirthdayToday();



}
