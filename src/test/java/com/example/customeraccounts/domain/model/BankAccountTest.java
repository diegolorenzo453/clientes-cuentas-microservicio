package com.example.customeraccounts.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankAccountTest {

    @Test
    void createsBankAccountWithValidAccountTypeAndBalance() {
        BankAccount bankAccount = new BankAccount(1L, "11111111A", AccountType.NORMAL, new BigDecimal("50000"));

        assertThat(bankAccount.getAccountType()).isEqualTo(AccountType.NORMAL);
        assertThat(bankAccount.getBalance()).isEqualByComparingTo("50000");
    }

    @Test
    void rejectsNegativeBalance() {
        BankAccount bankAccount = new BankAccount(1L, "11111111A", AccountType.NORMAL, BigDecimal.ZERO);

        assertThatThrownBy(() -> bankAccount.updateBalance(new BigDecimal("-1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bank account balance cannot be negative");
    }

    @Test
    void parsesAccountTypeIgnoringCase() {
        assertThat(AccountType.from("premium")).isEqualTo(AccountType.PREMIUM);
    }

    @Test
    void rejectsUnsupportedAccountType() {
        assertThatThrownBy(() -> AccountType.from("VIP"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported account type: VIP");
    }
}
