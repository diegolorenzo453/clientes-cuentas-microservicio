package com.example.customeraccounts.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class BankAccount {

    private final Long id;
    private final String customerNationalId;
    private final String accountType;
    private BigDecimal balance;

    public BankAccount(Long id, String customerNationalId, String accountType, BigDecimal balance) {
        this.id = id;
        this.customerNationalId = requireText(customerNationalId, "customerNationalId");
        this.accountType = requireText(accountType, "accountType").toUpperCase();
        updateBalance(balance);
    }

    public void updateBalance(BigDecimal balance) {
        BigDecimal newBalance = Objects.requireNonNull(balance, "balance");
        if (newBalance.signum() < 0) {
            throw new IllegalArgumentException("Bank account balance cannot be negative");
        }
        this.balance = newBalance;
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value.trim();
    }
}


