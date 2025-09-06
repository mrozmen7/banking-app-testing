package com.ozmenyavuz;

import com.ozmenyavuz.model.Account;
import com.ozmenyavuz.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testSaveAndFindById() {
        Account account = new Account(null, 20002L, "Yavuz", BigDecimal.valueOf(9000));
        Account saved = accountRepository.save(account);

        Optional<Account> found = accountRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getHolderName()).isEqualTo("Yavuz");
    }

    @Test
    void testFindByAccountNumber() {
        Account account = new Account(null, 67890L, "Yavuz", BigDecimal.valueOf(500));
        accountRepository.save(account);

        Account found = accountRepository.findByAccountNumber(67890L);

        assertThat(found).isNotNull();
        assertThat(found.getHolderName()).isEqualTo("Yavuz");
    }
}