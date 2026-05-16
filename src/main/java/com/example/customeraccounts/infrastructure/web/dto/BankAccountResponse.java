package com.example.customeraccounts.infrastructure.web.dto;

import com.example.customeraccounts.domain.model.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Bank account associated with a customer")
public record BankAccountResponse(
        @Schema(example = "1")
        Long id,

        @Schema(example = "11111111A")
        @JsonProperty("dniCliente")
        String customerNationalId,

        @Schema(example = "PREMIUM")
        @JsonProperty("tipoCuenta")
        AccountType accountType,

        @Schema(example = "150000")
        @JsonProperty("total")
        BigDecimal balance
) {
}


