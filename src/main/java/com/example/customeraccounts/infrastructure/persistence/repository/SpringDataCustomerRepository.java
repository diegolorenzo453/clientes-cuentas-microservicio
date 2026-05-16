package com.example.customeraccounts.infrastructure.persistence.repository;

import com.example.customeraccounts.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerJpaEntity, String> {
}


