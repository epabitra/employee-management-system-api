package com.nihar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.CountEmployeeDTO;
import com.nihar.dto.FullUserDetailsDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.dto.UserWithRoleDTO;
import com.nihar.entity.User;
import com.nihar.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * ✅ Public: Account creation
     */
    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody UserDetailsDTO dto) {
        try {
            System.out.println("Incoming DTO: " + new ObjectMapper().writeValueAsString(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userService.loginUser(dto.getEmail(), dto.getPasswordHash()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User createdUser = userService.addUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * 🔐 Admin only: Get all users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * 🔐 Admin only: Dashboard counter
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard-count")
    public ResponseEntity<AdminDashboardCounterDTO> getDashboardCount() {
        return ResponseEntity.ok(userService.getAdminDashboardCounter());
    }

    /**
     * 🔐 Admin or Manager: Employee count
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/count")
    public ResponseEntity<CountEmployeeDTO> getEmployeeCount() {
        return ResponseEntity.ok(userService.getEmployeeCount());
    }
    
    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUsers")
    public ResponseEntity<FullUserDetailsDTO> getCurrentUserDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getFullUserDetails(email));
    }




    /**
     * 🔐 Admin only: Secure test endpoint
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure")
    public ResponseEntity<String> secureData() {
        return ResponseEntity.ok("✅ Only ADMIN can access this secure data.");
    }
}
