package com.ozmenyavuz.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sourceAccountNumber;

    @Column(nullable = false)
    private Long targetAccountNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String description;

    public Transaction() {
    }

    public Transaction(Long sourceAccountNumber, Long targetAccountNumber, BigDecimal amount, String description) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.description = description;
    }


}
