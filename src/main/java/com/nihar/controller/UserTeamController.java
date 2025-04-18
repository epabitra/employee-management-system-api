package com.nihar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nihar.entity.UserTeam;
import com.nihar.service.UserTeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-teams")
@RequiredArgsConstructor
public class UserTeamController {

    private final UserTeamService service;

    @PostMapping
    public ResponseEntity<UserTeam> create(@RequestBody UserTeam obj) {
        return ResponseEntity.ok(service.save(obj));
    }

    @GetMapping
    public ResponseEntity<List<UserTeam>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTeam> getById(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

