package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.Salary;

public interface SalaryService {
    
    Salary save(Salary obj);
    
    List<Salary> findAll();
    
    Optional<Salary> findById(Long id);
    
    void delete(Long id);

    // ✅ New method to calculate total salaries
    double getTotalSalaries();
}
