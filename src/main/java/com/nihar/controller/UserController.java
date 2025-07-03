package com.nihar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.CountEmployeeDTO;
import com.nihar.dto.FullUserDetailsDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.entity.User;
import com.nihar.security.CustomUserDetailsService;
import com.nihar.service.UserService;
import com.nihar.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;


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

        // 1. Check if email already exists
        if (userService.loginUser(dto.getEmail(), dto.getPasswordHash()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // 2. Create the user
        User createdUser = userService.addUser(dto);

        // 3. Load UserDetails (for generating JWT with roles)
        UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());

        // 4. Generate JWT token
        String token = jwtUtil.generateToken(userDetails);

        // 5. Map full user info (DTO) if needed
        User user = createdUser;


        // 6. Return token + user info
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "token", token,
            "user", user
        ));
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
