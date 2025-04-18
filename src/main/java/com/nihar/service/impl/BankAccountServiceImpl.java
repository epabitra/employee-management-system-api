package com.nihar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nihar.entity.BankAccount;
import com.nihar.repository.BankAccountRepository;
import com.nihar.service.BankAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository repo;

    public BankAccount save(BankAccount obj) { return repo.save(obj); }
    public List<BankAccount> findAll() { return repo.findAll(); }
    public Optional<BankAccount> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}

