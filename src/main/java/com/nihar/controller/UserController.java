package com.nihar.controller;

import com.nihar.dto.AdminDashboardCounterDTO;
import com.nihar.dto.UserDetailsDTO;
import com.nihar.entity.User;
import com.nihar.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDetailsDTO dto) {
        return ResponseEntity.ok(userService.addUser(dto));
    }
    
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/dashboard-count")
    public ResponseEntity<AdminDashboardCounterDTO> getDashboardCount() {
        return ResponseEntity.ok(userService.getAdminDashboardCounter());
    }
}
