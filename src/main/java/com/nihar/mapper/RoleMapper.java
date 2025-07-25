package com.nihar.mapper;

import com.nihar.dto.RoleDto;
import com.nihar.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role) {
        if (role == null) return null;

        return RoleDto.builder()
                .id(role.getId())
                .uuid(role.getUuid())
                .name(role.getName())
                .build();
    }

    public Role toEntity(RoleDto dto) {
        if (dto == null) return null;

        Role role = new Role();
        role.setId(dto.getId());
        role.setUuid(dto.getUuid());
        role.setName(dto.getName());
        return role;
    }
}
