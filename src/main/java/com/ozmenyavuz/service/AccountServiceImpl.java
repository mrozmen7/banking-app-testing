package com.ozmenyavuz.service;


import com.ozmenyavuz.exception.AccountNotFoundException;
import com.ozmenyavuz.model.Account;
import com.ozmenyavuz.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account deposit(Long id, BigDecimal amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Override
    public Account withdraw(Long id, BigDecimal amount) {
        Account account = getAccountById(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}