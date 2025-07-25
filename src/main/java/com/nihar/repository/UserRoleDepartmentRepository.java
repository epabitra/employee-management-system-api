package com.nihar.repository;

import com.nihar.entity.UserRoleDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDepartmentRepository extends JpaRepository<UserRoleDepartment, Long> {
    // You can add custom query methods if needed
}
