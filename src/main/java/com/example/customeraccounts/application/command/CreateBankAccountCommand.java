package com.example.customeraccounts.application.command;

import java.math.BigDecimal;
import java.util.Optional;

public record CreateBankAccountCommand(
        String customerNationalId,
        String accountType,
        BigDecimal balance,
        NewCustomerCommand newCustomer
) {
    public Optional<NewCustomerCommand> optionalNewCustomer() {
        return Optional.ofNullable(newCustomer);
    }
}


