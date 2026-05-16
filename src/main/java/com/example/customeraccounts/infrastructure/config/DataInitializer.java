package com.example.customeraccounts.infrastructure.config;

import com.example.customeraccounts.domain.model.AccountType;
import com.example.customeraccounts.domain.model.BankAccount;
import com.example.customeraccounts.domain.model.Customer;
import com.example.customeraccounts.domain.port.CustomerRepositoryPort;
import com.example.customeraccounts.domain.port.BankAccountRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class DataInitializer {

    private static final DateTimeFormatter SPANISH_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Bean
    CommandLineRunner loadInitialData(CustomerRepositoryPort customerRepository,
                                      BankAccountRepositoryPort bankAccountRepository) {
        return args -> {
            List<Customer> customers = List.of(
                    customer("11111111A", "Juan", "Perez", "Lopez", "12/09/1959"),
                    customer("22222222B", "Raul", "Canales", "Rodriguez", "01/03/1985"),
                    customer("33333333C", "Elena", "Ruiz", "Herrera", "10/05/2010"),
                    customer("44444444D", "Raquel", "Ruiz", "Herrera", "21/06/2002"),
                    customer("55555555E", "Maria", "Sanchez", "Torres", "08/08/1999")
            );

            customers.forEach(customerRepository::save);

            List<BankAccount> bankAccounts = List.of(
                    bankAccount("11111111A", AccountType.PREMIUM, "150000"),
                    bankAccount("11111111A", AccountType.NORMAL, "20000"),
                    bankAccount("22222222B", AccountType.NORMAL, "50000"),
                    bankAccount("22222222B", AccountType.JUNIOR, "300"),
                    bankAccount("33333333C", AccountType.JUNIOR, "300"),
                    bankAccount("44444444D", AccountType.NORMAL, "75000"),
                    bankAccount("55555555E", AccountType.PREMIUM, "120000")
            );

            bankAccounts.forEach(bankAccountRepository::save);
        };
    }

    private Customer customer(String nationalId, String firstName, String firstSurname, String secondSurname, String birthDate) {
        return new Customer(nationalId, firstName, firstSurname, secondSurname, LocalDate.parse(birthDate, SPANISH_DATE));
    }

    private BankAccount bankAccount(String customerNationalId, AccountType accountType, String balance) {
        return new BankAccount(null, customerNationalId, accountType, new BigDecimal(balance));
    }
}


