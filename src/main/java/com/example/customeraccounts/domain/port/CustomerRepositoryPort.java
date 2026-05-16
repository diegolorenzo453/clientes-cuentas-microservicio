package com.example.customeraccounts.domain.port;

import com.example.customeraccounts.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {

    List<Customer> findAll();

    Optional<Customer> findByNationalId(String nationalId);

    Customer save(Customer customer);
}


