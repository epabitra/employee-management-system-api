package com.nihar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nihar.entity.UserRoleDepartment;
import com.nihar.service.UserRoleDepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-role-departments")
@RequiredArgsConstructor
public class UserRoleDepartmentController {
	@Autowired 
    private final UserRoleDepartmentService service;

    @PostMapping
    public ResponseEntity<UserRoleDepartment> create(@RequestBody UserRoleDepartment obj) {
        return ResponseEntity.ok(service.save(obj));
    }

    @GetMapping
    public ResponseEntity<List<UserRoleDepartment>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDepartment> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

