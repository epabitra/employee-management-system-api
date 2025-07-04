package com.nihar.service;

import com.nihar.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getAllRoles();
    
    Optional<Role> findByUuid(String uuid); // ✅ Newly added
    
    Optional<Role> findById(Long id);
}
