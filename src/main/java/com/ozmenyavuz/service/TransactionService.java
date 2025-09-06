package com.ozmenyavuz.service;


import com.ozmenyavuz.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
}