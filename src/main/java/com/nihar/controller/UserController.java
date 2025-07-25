package com.nihar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.DepartmentEmployeeCountDTO;
import com.nihar.dto.FullUserDetailsDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.entity.User;
import com.nihar.security.CustomUserDetailsService;
import com.nihar.service.MailService;
import com.nihar.service.UserService;
import com.nihar.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final MailService mailService;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * ✅ Public: Signup + return token with roles
     */
    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody UserDetailsDTO dto) {
        try {
            System.out.println("Incoming DTO: " + new ObjectMapper().writeValueAsString(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. Check if email already exists
        if (userService.loginUser(dto.getEmail(), dto.getPasswordHash()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // 2. Create the user
        User createdUser = userService.addUser(dto);

        // 3. Assign Role & Department to User (Required before generating token)
        userService.assignRoleAndDepartment(
        	    createdUser.getId(),
        	    dto.getRoleUuid(),
        	    dto.getDepartmentUuid()
        	);


        // 4. Load user with authorities (so token includes roles)
        UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());

        // 5. Generate JWT token
        String token = jwtUtil.generateToken(userDetails);
        
     // ✅ Send email after user is created
        try {
            String subject = "Welcome to the Company!";
            String message = "Hi " + createdUser.getFullName() + ",\n\n" +
                             "Your account has been created successfully.\n" +
                             "Email: " + createdUser.getEmail() + "\n" +
                             "Please contact HR to set up your password.\n\n" +
                             "Regards,\nCompany Admin";

            mailService.sendWelcomeEmail(createdUser.getEmail(), subject, message);
        } catch (Exception e) {
            e.printStackTrace(); // log error or show message
        }

        // 6. Return response
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "token", token,
                "user", createdUser
        ));
    }

  

    
    /**
     * 🔐 Admin : Dashboard summary with total employees, teams, salary, and departments
     */
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/count")
    public ResponseEntity<AdminDashboardCounterDTO> getDashboardSummary() {
        return ResponseEntity.ok(userService.getAdminDashboardCounter());
    }
    
    
    /**
     * 🔐 Admin : Dashboard summaryfor pie chart
     */
    
    @GetMapping("/employees-per-department")
    public List<DepartmentEmployeeCountDTO> getEmployeeCountPerDepartment() {
        return userService.getEmployeeCountGroupedByDepartment();
    }
    
    
    /**
     * 🔐 Admin :  Birthday today
     */
    
    @GetMapping("/birthday-today")
    public ResponseEntity<List<String>> getTodaysBirthdays() {
        List<String> birthdayUsers = userService.getUsersWithBirthdayToday();
        return ResponseEntity.ok(birthdayUsers);
    }




    /**
     * ✅ Check current login info
     */
    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

    /**
     * 🔐 Admin only: Get current user full details
     */
   
    @GetMapping("/getUsers")
    public ResponseEntity<FullUserDetailsDTO> getCurrentUserDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getFullUserDetails(email));
    }

   
}
