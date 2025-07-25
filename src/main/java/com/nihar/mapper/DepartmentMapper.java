package com.nihar.mapper;

import com.nihar.dto.DepartmentDto;
import com.nihar.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department department) {
        if (department == null) return null;

        return DepartmentDto.builder()
                .id(department.getId())
                .uuid(department.getUuid())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }

    public Department toEntity(DepartmentDto dto) {
        if (dto == null) return null;

        Department dept = new Department();
        dept.setId(dto.getId());
        dept.setUuid(dto.getUuid());
        dept.setName(dto.getName());
        dept.setDescription(dto.getDescription());
        return dept;
    }
}
