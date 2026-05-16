package com.example.customeraccounts.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Customer with associated bank accounts")
public record CustomerResponse(
        @Schema(example = "11111111A")
        @JsonProperty("dni")
        String nationalId,

        @Schema(example = "Juan")
        @JsonProperty("nombre")
        String firstName,

        @Schema(example = "Perez")
        @JsonProperty("apellido1")
        String firstSurname,

        @Schema(example = "Lopez")
        @JsonProperty("apellido2")
        String secondSurname,

        @Schema(example = "1959-09-12")
        @JsonProperty("fechaNacimiento")
        LocalDate birthDate,

        @JsonProperty("cuentas")
        List<BankAccountResponse> bankAccounts
) {
}


