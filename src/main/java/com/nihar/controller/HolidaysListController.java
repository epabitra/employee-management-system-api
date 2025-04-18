package com.nihar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nihar.entity.HolidaysList;
import com.nihar.service.HolidaysListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
public class HolidaysListController {
    private final HolidaysListService service;

    @PostMapping
    public ResponseEntity<HolidaysList> create(@RequestBody HolidaysList obj) { return ResponseEntity.ok(service.save(obj)); }

    @GetMapping
    public ResponseEntity<List<HolidaysList>> getAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<HolidaysList> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
