package com.nihar.controller;

import com.nihar.dto.RoleDto;
import com.nihar.entity.Role;
import com.nihar.mapper.RoleMapper;
import com.nihar.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto dto) {
        Role role = roleMapper.toEntity(dto);
        Role saved = roleService.createRole(role);
        return ResponseEntity.ok(roleMapper.toDto(saved));
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> dtos = roleService.getAllRoles()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(roleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
