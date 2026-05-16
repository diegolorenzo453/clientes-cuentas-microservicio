package com.example.customeraccounts.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Schema(description = "Optional customer data used when creating an account for a new customer")
public record NewCustomerRequest(
        @Schema(example = "Laura")
        @JsonProperty("nombre")
        @NotBlank String firstName,

        @Schema(example = "Garcia")
        @JsonProperty("apellido1")
        @NotBlank String firstSurname,

        @Schema(example = "Martin")
        @JsonProperty("apellido2")
        String secondSurname,

        @Schema(example = "1990-04-15")
        @JsonProperty("fechaNacimiento")
        @NotNull @PastOrPresent LocalDate birthDate
) {
}


