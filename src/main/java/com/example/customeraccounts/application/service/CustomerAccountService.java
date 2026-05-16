package com.example.customeraccounts.application.service;

import com.example.customeraccounts.application.command.UpdateBankAccountBalanceCommand;
import com.example.customeraccounts.application.command.CreateBankAccountCommand;
import com.example.customeraccounts.domain.model.Customer;
import com.example.customeraccounts.domain.model.BankAccount;
import com.example.customeraccounts.domain.port.CustomerRepositoryPort;
import com.example.customeraccounts.domain.port.BankAccountRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerAccountService {

    private final CustomerRepositoryPort customerRepository;
    private final BankAccountRepositoryPort bankAccountRepository;

    public CustomerAccountService(CustomerRepositoryPort customerRepository, BankAccountRepositoryPort bankAccountRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomersWithAccounts() {
        return loadCustomersWithAccounts();
    }

    @Transactional(readOnly = true)
    public List<Customer> findAdultCustomers() {
        LocalDate today = LocalDate.now();
        return loadCustomersWithAccounts().stream()
                .filter(customer -> customer.isAdult(today))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomersWithTotalBalanceGreaterThan(BigDecimal amount) {
        return loadCustomersWithAccounts().stream()
                .filter(customer -> customer.hasTotalBalanceGreaterThan(amount))
                .toList();
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByNationalId(String nationalId) {
        return customerRepository.findByNationalId(nationalId)
                .map(this::withBankAccounts)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with national id " + nationalId));
    }

    @Transactional
    public BankAccount createBankAccount(CreateBankAccountCommand command) {
        Customer customer = customerRepository.findByNationalId(command.customerNationalId())
                .or(() -> command.optionalNewCustomer().map(customerData -> new Customer(
                        command.customerNationalId(),
                        customerData.firstName(),
                        customerData.firstSurname(),
                        customerData.secondSurname(),
                        customerData.birthDate()
                )))
                .orElseGet(() -> new Customer(command.customerNationalId(), "Unknown", "No data", "", LocalDate.now()));

        Customer savedCustomer = customerRepository.save(customer);

        return bankAccountRepository.save(new BankAccount(null, savedCustomer.getNationalId(), command.accountType(), command.balance()));
    }

    @Transactional
    public BankAccount updateBankAccountBalance(Long accountId, UpdateBankAccountBalanceCommand command) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found with id " + accountId));
        bankAccount.updateBalance(command.balance());
        return bankAccountRepository.save(bankAccount);
    }

    private Customer withBankAccounts(Customer customer) {
        customer.replaceBankAccounts(bankAccountRepository.findByCustomerNationalId(customer.getNationalId()));
        return customer;
    }

    private List<Customer> loadCustomersWithAccounts() {
        return customerRepository.findAll().stream()
                .map(this::withBankAccounts)
                .toList();
    }

}


