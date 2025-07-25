package com.nihar.service.impl;

import com.nihar.entity.Role;
import com.nihar.repository.RoleRepository;
import com.nihar.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findByUuid(String uuid) {
        return roleRepository.findByUuid(uuid);
    }
    
    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }
    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

}
