package com.example.customeraccounts.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void isAdultReturnsTrueWhenCustomerIsAtLeastEighteenYearsOld() {
        Customer customer = new Customer("11111111A", "Juan", "Perez", "Lopez", LocalDate.of(2000, 1, 1));

        assertThat(customer.isAdult(LocalDate.of(2018, 1, 1))).isTrue();
    }

    @Test
    void isAdultReturnsFalseWhenCustomerIsYoungerThanEighteenYearsOld() {
        Customer customer = new Customer("33333333C", "Elena", "Ruiz", "Herrera", LocalDate.of(2010, 5, 10));

        assertThat(customer.isAdult(LocalDate.of(2026, 5, 16))).isFalse();
    }

    @Test
    void totalBalanceAddsAllBankAccountBalances() {
        Customer customer = new Customer("11111111A", "Juan", "Perez", "Lopez", LocalDate.of(1959, 9, 12));
        customer.replaceBankAccounts(List.of(
                new BankAccount(1L, "11111111A", AccountType.PREMIUM, new BigDecimal("150000")),
                new BankAccount(2L, "11111111A", AccountType.NORMAL, new BigDecimal("20000"))
        ));

        assertThat(customer.totalBalance()).isEqualByComparingTo("170000");
    }
}
