package com.ozmenyavuz.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDto {
    private Long accountNumber;
    private String holderName;
    private BigDecimal balance;
    private BigDecimal dailyLimit;
}