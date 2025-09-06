package com.ozmenyavuz.service;

import com.ozmenyavuz.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountById(Long id);

    List<Account> getAllAccounts();

    Account deposit(Long id, BigDecimal amount);

    Account withdraw(Long id, BigDecimal amount);

    void deleteAccount(Long id);
}