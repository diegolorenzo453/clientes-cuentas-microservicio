package com.example.customeraccounts.application.command;

import java.time.LocalDate;

public record NewCustomerCommand(
        String firstName,
        String firstSurname,
        String secondSurname,
        LocalDate birthDate
) {
}


