package com.example.customeraccounts.infrastructure.persistence.adapter;

import com.example.customeraccounts.domain.model.BankAccount;
import com.example.customeraccounts.domain.port.BankAccountRepositoryPort;
import com.example.customeraccounts.infrastructure.persistence.entity.BankAccountJpaEntity;
import com.example.customeraccounts.infrastructure.persistence.repository.SpringDataBankAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class BankAccountPersistenceAdapter implements BankAccountRepositoryPort {

    private final SpringDataBankAccountRepository repository;

    public BankAccountPersistenceAdapter(SpringDataBankAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BankAccount> findByCustomerNationalId(String customerNationalId) {
        return repository.findByCustomerNationalId(customerNationalId).stream()
                .map(this::toDomain)
                .sorted(Comparator.comparing(BankAccount::getId))
                .toList();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        return toDomain(repository.save(toEntity(bankAccount)));
    }

    private BankAccount toDomain(BankAccountJpaEntity entity) {
        return new BankAccount(entity.getId(), entity.getCustomerNationalId(), entity.getAccountType(), entity.getBalance());
    }

    private BankAccountJpaEntity toEntity(BankAccount bankAccount) {
        return new BankAccountJpaEntity(
                bankAccount.getId(),
                bankAccount.getCustomerNationalId(),
                bankAccount.getAccountType(),
                bankAccount.getBalance()
        );
    }
}


