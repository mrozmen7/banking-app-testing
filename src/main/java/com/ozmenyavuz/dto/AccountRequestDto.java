package com.ozmenyavuz.dto;


import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {

    @NotNull
    private Long accountNumber;

    @NotBlank
    private String holderName;

    @NotNull
    @Min(value = 0, message = "Balance must be non-negative")
    private BigDecimal balance;

    private BigDecimal dailyLimit;
}