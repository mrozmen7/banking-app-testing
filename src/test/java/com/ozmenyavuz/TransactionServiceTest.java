package com.ozmenyavuz;
import com.ozmenyavuz.model.Transaction;
import com.ozmenyavuz.repository.TransactionRepository;
import com.ozmenyavuz.service.TransactionService;
import com.ozmenyavuz.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionRepository transactionRepository; // mock olacak
    private TransactionService transactionService;       // test edeceğimiz sınıf

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void createTransaction_should_save_via_repository() {
        // Arrange
        Transaction tx = new Transaction();
        tx.setSourceAccountNumber(11111L);
        tx.setTargetAccountNumber(22222L);
        tx.setAmount(BigDecimal.valueOf(99.99));
        tx.setDescription("Service test");
        tx.setCreatedAt(LocalDateTime.now());

        // Repository save çağrılınca "saved" dönsün
        Transaction saved = new Transaction();
        saved.setSourceAccountNumber(11111L);
        saved.setTargetAccountNumber(22222L);
        saved.setAmount(BigDecimal.valueOf(99.99));
        saved.setDescription("Service test");
        saved.setCreatedAt(tx.getCreatedAt());
        // id’yi kayıttan sonra DB verir; mock içinde biz verelim
        // (gerçekte repository.save bunu döndürürdü)
        // setter varsa:
        // reflection veya setter ile set edebilirsin; yoksa id kontrolü yapma
        // id kontrolünü basitlik için atlıyoruz

        when(transactionRepository.save(any(Transaction.class))).thenReturn(saved);

        // Act
        Transaction result = transactionService.createTransaction(tx);

        // Assert
        assertThat(result.getAmount()).isEqualByComparingTo("99.99");

        // Gerçekten repository.save doğru objeyle çağrılmış mı?
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(captor.capture());
        assertThat(captor.getValue().getSourceAccountNumber()).isEqualTo(11111L);
    }

    @Test
    void getTransactionById_should_throw_when_not_found() {
        // Arrange
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.getTransactionById(999L));

        assertThat(ex.getMessage()).contains("Transaction not found");
    }
}