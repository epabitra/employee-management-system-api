package com.nihar.controller;

import com.nihar.dto.DepartmentDto;
import com.nihar.entity.Department;
import com.nihar.mapper.DepartmentMapper;
import com.nihar.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto dto) {
        Department department = departmentMapper.toEntity(dto);
        Department saved = departmentService.createDepartment(department);
        return ResponseEntity.ok(departmentMapper.toDto(saved));
    }

    @GetMapping("/list")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> dtos = departmentService.getAllDepartments()
                .stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return departmentService.findById(id)
                .map(departmentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
