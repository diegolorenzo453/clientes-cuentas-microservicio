package com.example.customeraccounts.infrastructure.web.dto;

import com.example.customeraccounts.domain.model.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Request to create a bank account")
public record CreateBankAccountRequest(
        @Schema(example = "11111111A")
        @JsonProperty("dniCliente")
        @NotBlank String customerNationalId,

        @Schema(example = "NORMAL", allowableValues = {"NORMAL", "PREMIUM", "JUNIOR"})
        @JsonProperty("tipoCuenta")
        @NotNull AccountType accountType,

        @Schema(example = "50000")
        @JsonProperty("total")
        @NotNull @DecimalMin("0.0") BigDecimal balance,

        @JsonProperty("cliente")
        @Valid NewCustomerRequest customer
) {
}


