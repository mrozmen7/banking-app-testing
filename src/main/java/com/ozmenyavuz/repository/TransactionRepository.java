package com.ozmenyavuz.repository;

import com.ozmenyavuz.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountNumber(Long sourceAccountNumber);
    List<Transaction> findByTargetAccountNumber(Long targetAccountNumber);
}