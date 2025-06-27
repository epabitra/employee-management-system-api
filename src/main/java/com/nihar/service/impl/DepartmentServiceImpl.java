package com.nihar.service.impl;

import com.nihar.entity.Department;
import com.nihar.repository.DepartmentRepository;
import com.nihar.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> findByUuid(String uuid) {
        return departmentRepository.findByUuid(uuid); // ✅ implements the missing method
    }
}
