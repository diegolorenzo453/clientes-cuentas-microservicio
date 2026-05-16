package com.example.customeraccounts.application.command;

import com.example.customeraccounts.domain.model.AccountType;

import java.math.BigDecimal;
import java.util.Optional;

public record CreateBankAccountCommand(
        String customerNationalId,
        AccountType accountType,
        BigDecimal balance,
        NewCustomerCommand newCustomer
) {
    public Optional<NewCustomerCommand> optionalNewCustomer() {
        return Optional.ofNullable(newCustomer);
    }
}


