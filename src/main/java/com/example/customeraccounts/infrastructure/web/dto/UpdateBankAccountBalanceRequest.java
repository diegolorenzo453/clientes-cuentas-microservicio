package com.example.customeraccounts.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Request to update a bank account balance")
public record UpdateBankAccountBalanceRequest(
        @Schema(example = "180000")
        @JsonProperty("total")
        @NotNull @DecimalMin("0.0") BigDecimal balance
) {
}


