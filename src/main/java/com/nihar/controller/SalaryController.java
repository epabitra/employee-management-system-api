package com.nihar.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nihar.entity.Salary;
import com.nihar.service.SalaryService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/salaries")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService service;

    @PostMapping
    public ResponseEntity<Salary> create(@RequestBody Salary obj) { return ResponseEntity.ok(service.save(obj)); }

    @GetMapping
    public ResponseEntity<List<Salary>> getAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
