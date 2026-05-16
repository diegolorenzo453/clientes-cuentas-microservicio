package com.example.customeraccounts.infrastructure.persistence.adapter;

import com.example.customeraccounts.domain.model.Customer;
import com.example.customeraccounts.domain.port.CustomerRepositoryPort;
import com.example.customeraccounts.infrastructure.persistence.entity.CustomerJpaEntity;
import com.example.customeraccounts.infrastructure.persistence.repository.SpringDataCustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

@Repository
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;

    public CustomerPersistenceAdapter(SpringDataCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .sorted(Comparator.comparing(Customer::getNationalId))
                .toList();
    }

    @Override
    public Optional<Customer> findByNationalId(String nationalId) {
        return repository.findById(nationalId).map(this::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        return toDomain(repository.save(toEntity(customer)));
    }

    private Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(
                entity.getNationalId(),
                entity.getFirstName(),
                entity.getFirstSurname(),
                entity.getSecondSurname(),
                entity.getBirthDate()
        );
    }

    private CustomerJpaEntity toEntity(Customer customer) {
        return new CustomerJpaEntity(
                customer.getNationalId(),
                customer.getFirstName(),
                customer.getFirstSurname(),
                customer.getSecondSurname(),
                customer.getBirthDate()
        );
    }
}


