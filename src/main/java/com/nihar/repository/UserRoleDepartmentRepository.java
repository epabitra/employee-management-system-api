package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nihar.entity.UserRoleDepartment;

@Repository
public interface UserRoleDepartmentRepository extends JpaRepository<UserRoleDepartment, Long> {}

