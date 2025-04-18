package com.nihar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.nihar.entity.UserActivity;
import com.nihar.service.UserActivityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-activities")
@RequiredArgsConstructor
public class UserActivityController {
    private final UserActivityService service;

    @PostMapping
    public ResponseEntity<UserActivity> create(@RequestBody UserActivity obj) { return ResponseEntity.ok(service.save(obj)); }

    @GetMapping
    public ResponseEntity<List<UserActivity>> getAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<UserActivity> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
