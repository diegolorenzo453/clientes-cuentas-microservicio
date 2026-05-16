package com.example.customeraccounts.domain.port;

import com.example.customeraccounts.domain.model.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepositoryPort {

    List<BankAccount> findByCustomerNationalId(String customerNationalId);

    Optional<BankAccount> findById(Long id);

    BankAccount save(BankAccount bankAccount);
}


