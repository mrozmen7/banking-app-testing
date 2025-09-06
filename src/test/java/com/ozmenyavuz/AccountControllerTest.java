package com.ozmenyavuz;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozmenyavuz.controller.AccountController;
import com.ozmenyavuz.dto.AccountRequestDto;
import com.ozmenyavuz.model.Account;
import com.ozmenyavuz.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAccount() throws Exception {
        AccountRequestDto requestDto = new AccountRequestDto(12345L, "Ali", BigDecimal.valueOf(1000));

        Account savedAccount = new Account(1L, 12345L, "Ali", BigDecimal.valueOf(1000));

        // Mock davranışı tanımlıyoruz
        when(accountService.createAccount(any(Account.class))).thenReturn(savedAccount);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value(12345))
                .andExpect(jsonPath("$.holderName").value("Ali"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void testGetAccountById() throws Exception {
        Account account = new Account(1L, 12345L, "Veli", BigDecimal.valueOf(500));
        when(accountService.getAccountById(1L)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.holderName").value("Veli"))
                .andExpect(jsonPath("$.balance").value(500));
    }

    @Test
    void testGetAllAccounts() throws Exception {
        Account account = new Account(1L, 12345L, "Ayşe", BigDecimal.valueOf(200));
        when(accountService.getAllAccounts()).thenReturn(Collections.singletonList(account));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].holderName").value("Ayşe"))
                .andExpect(jsonPath("$[0].balance").value(200));
    }
}