package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.UserRoleDepartment;

public interface UserRoleDepartmentService {
    UserRoleDepartment save(UserRoleDepartment obj);
    List<UserRoleDepartment> findAll();
    Optional<UserRoleDepartment> findById(Long id);
    void delete(Long id);
}
