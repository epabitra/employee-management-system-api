package com.nihar.service;

import com.nihar.entity.Department;
import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();
}
