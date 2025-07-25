package com.nihar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nihar.entity.Salary;
import com.nihar.repository.SalaryRepository;
import com.nihar.service.SalaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;

    @Override
    public Salary save(Salary obj) {
        return salaryRepository.save(obj);
    }

    @Override
    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

    @Override
    public Optional<Salary> findById(Long id) {
        return salaryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        salaryRepository.deleteById(id);
    }

    @Override
    public double getTotalSalaries() {
        return salaryRepository.sumAllSalaries();
    }
}
