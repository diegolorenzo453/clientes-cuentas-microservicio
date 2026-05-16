package com.example.customeraccounts.application.command;

import java.math.BigDecimal;

public record UpdateBankAccountBalanceCommand(BigDecimal balance) {
}


