package com.ozmenyavuz;


import com.ozmenyavuz.model.Transaction;
import com.ozmenyavuz.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // PostgreSQL kullanalım
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void saveAndFindTransaction() {
        // Arrange
        Transaction tx = new Transaction(11111L, 22222L, BigDecimal.valueOf(500.00), "Test transfer");

        // Act
        Transaction saved = transactionRepository.save(tx);

        // Assert →
        List<Transaction> fromSource = transactionRepository.findBySourceAccountNumber(11111L);
        assertThat(fromSource).isNotEmpty();
        assertThat(fromSource.get(0).getTargetAccountNumber()).isEqualTo(22222L);
    }
}