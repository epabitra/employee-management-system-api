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
    private final SalaryRepository repo;
    public Salary save(Salary obj) { return repo.save(obj); }
    public List<Salary> findAll() { return repo.findAll(); }
    public Optional<Salary> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}
