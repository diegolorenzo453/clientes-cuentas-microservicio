package com.example.customeraccounts.infrastructure.persistence.entity;

import com.example.customeraccounts.domain.model.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerNationalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal balance;

    public BankAccountJpaEntity(Long id, String customerNationalId, AccountType accountType, BigDecimal balance) {
        this.id = id;
        this.customerNationalId = customerNationalId;
        this.accountType = accountType;
        this.balance = balance;
    }
}


