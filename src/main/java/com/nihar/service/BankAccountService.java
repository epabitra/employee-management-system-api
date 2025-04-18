package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.BankAccount;

public interface BankAccountService {
    BankAccount save(BankAccount obj);
    List<BankAccount> findAll();
    Optional<BankAccount> findById(Long id);
    void delete(Long id);
}

