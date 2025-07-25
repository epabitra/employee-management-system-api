package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nihar.entity.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    @Query("SELECT COALESCE(SUM(s.basicSalary + s.bonus + s.allowance - s.deductions), 0) FROM Salary s WHERE s.active = true")
    double sumAllSalaries();

}
