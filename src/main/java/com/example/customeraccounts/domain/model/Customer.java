package com.example.customeraccounts.domain.model;

import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Customer {

    private final String nationalId;
    private final String firstName;
    private final String firstSurname;
    private final String secondSurname;
    private final LocalDate birthDate;
    @Getter(AccessLevel.NONE)
    private final List<BankAccount> bankAccounts = new ArrayList<>();

    public Customer(String nationalId, String firstName, String firstSurname, String secondSurname, LocalDate birthDate) {
        this.nationalId = requireText(nationalId, "nationalId");
        this.firstName = requireText(firstName, "firstName");
        this.firstSurname = requireText(firstSurname, "firstSurname");
        this.secondSurname = Objects.requireNonNullElse(secondSurname, "");
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate");
    }

    public List<BankAccount> getBankAccounts() {
        return Collections.unmodifiableList(bankAccounts);
    }

    public void replaceBankAccounts(List<BankAccount> newBankAccounts) {
        bankAccounts.clear();
        bankAccounts.addAll(newBankAccounts);
    }

    public boolean isAdult(LocalDate referenceDate) {
        return Period.between(birthDate, referenceDate).getYears() >= 18;
    }

    public BigDecimal totalBalance() {
        return bankAccounts.stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean hasTotalBalanceGreaterThan(BigDecimal amount) {
        return totalBalance().compareTo(amount) > 0;
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value.trim();
    }
}


