package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nihar.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount ,Long> {

}
