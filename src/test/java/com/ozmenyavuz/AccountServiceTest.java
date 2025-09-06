package com.ozmenyavuz;


import com.ozmenyavuz.exception.AccountNotFoundException;
import com.ozmenyavuz.model.Account;
import com.ozmenyavuz.repository.AccountRepository;
import com.ozmenyavuz.service.AccountService;
import com.ozmenyavuz.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class); // sahte repo
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void testCreateAccount() {
        Account account = new Account(1L, 12345L, "Ali", BigDecimal.valueOf(1000));
        when(accountRepository.save(account)).thenReturn(account);

        Account saved = accountService.createAccount(account);

        assertNotNull(saved);
        assertEquals("Ali", saved.getHolderName());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetAccountById_found() {
        Account account = new Account(1L, 12345L, "Veli", BigDecimal.valueOf(500));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account found = accountService.getAccountById(1L);

        assertNotNull(found);
        assertEquals("Veli", found.getHolderName());
    }

    @Test
    void testGetAccountById_notFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(99L));
    }

    @Test
    void testDeposit() {
        Account account = new Account(1L, 12345L, "Ayşe", BigDecimal.valueOf(100));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        Account updated = accountService.deposit(1L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), updated.getBalance());
    }

    @Test
    void testWithdraw_success() {
        Account account = new Account(1L, 12345L, "Can", BigDecimal.valueOf(200));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        Account updated = accountService.withdraw(1L, BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100), updated.getBalance());
    }

    @Test
    void testWithdraw_insufficientBalance() {
        Account account = new Account(1L, 12345L, "Zeynep", BigDecimal.valueOf(50));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class, () -> accountService.withdraw(1L, BigDecimal.valueOf(100)));
    }
}