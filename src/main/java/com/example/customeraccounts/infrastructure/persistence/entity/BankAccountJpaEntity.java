package com.example.customeraccounts.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String accountType;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal balance;

    public BankAccountJpaEntity(Long id, String customerNationalId, String accountType, BigDecimal balance) {
        this.id = id;
        this.customerNationalId = customerNationalId;
        this.accountType = accountType;
        this.balance = balance;
    }
}


