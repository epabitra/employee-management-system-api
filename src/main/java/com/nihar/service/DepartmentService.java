package com.nihar.service;

import com.nihar.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();

    Optional<Department> findByUuid(String uuid); // ✅ Newly added
}
