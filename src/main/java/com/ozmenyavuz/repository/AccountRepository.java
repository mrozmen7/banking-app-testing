package com.ozmenyavuz.repository;

import com.ozmenyavuz.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Long accountNumber);
}
