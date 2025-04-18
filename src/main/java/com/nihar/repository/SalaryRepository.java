package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nihar.entity.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary , Long> {

}
