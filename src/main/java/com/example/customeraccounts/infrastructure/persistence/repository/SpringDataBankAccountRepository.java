package com.example.customeraccounts.infrastructure.persistence.repository;

import com.example.customeraccounts.infrastructure.persistence.entity.BankAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataBankAccountRepository extends JpaRepository<BankAccountJpaEntity, Long> {

    List<BankAccountJpaEntity> findByCustomerNationalId(String customerNationalId);
}


