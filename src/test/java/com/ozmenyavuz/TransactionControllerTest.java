package com.ozmenyavuz;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozmenyavuz.controller.TransactionController;
import com.ozmenyavuz.model.Transaction;
import com.ozmenyavuz.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void post_createTransaction_should_return_201_and_body() throws Exception {
        // Request body entity Postman

        // Arrange (fake)
        Transaction request = new Transaction();
        request.setSourceAccountNumber(11111L);
        request.setTargetAccountNumber(22222L);
        request.setAmount(BigDecimal.valueOf(150.00));
        request.setDescription("API test");

        // in Service "saved" response
        Transaction response = new Transaction();
        response.setSourceAccountNumber(11111L);
        response.setTargetAccountNumber(22222L);
        response.setAmount(BigDecimal.valueOf(150.00));
        response.setDescription("API test");
        response.setCreatedAt(LocalDateTime.now());

        // Mockito wiht face mocking
        when(transactionService.createTransaction(org.mockito.ArgumentMatchers.any(Transaction.class)))
                .thenReturn(response);

        // Act start
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // Assert control
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount", is(150.00)));
    }

    @Test
    void get_by_id_should_return_200_and_body() throws Exception {
        Transaction tx = new Transaction();
        tx.setSourceAccountNumber(1L);
        tx.setTargetAccountNumber(2L);
        tx.setAmount(BigDecimal.valueOf(10));
        tx.setDescription("ok");
        tx.setCreatedAt(LocalDateTime.now());

        when(transactionService.getTransactionById(5L)).thenReturn(tx);

        mockMvc.perform(get("/api/transactions/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceAccountNumber", is(1)));
    }

    @Test
    void get_all_should_return_list() throws Exception {
        Transaction a = new Transaction();
        a.setSourceAccountNumber(1L);
        a.setTargetAccountNumber(2L);
        a.setAmount(BigDecimal.valueOf(10));
        a.setDescription("a");
        a.setCreatedAt(LocalDateTime.now());

        Transaction b = new Transaction();
        b.setSourceAccountNumber(3L);
        b.setTargetAccountNumber(4L);
        b.setAmount(BigDecimal.valueOf(20));
        b.setDescription("b");
        b.setCreatedAt(LocalDateTime.now());

        when(transactionService.getAllTransactions()).thenReturn(List.of(a, b));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].targetAccountNumber", is(2)));
    }
}