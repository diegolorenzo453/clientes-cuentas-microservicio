package com.example.customeraccounts.infrastructure.web.mapper;

import com.example.customeraccounts.application.command.UpdateBankAccountBalanceCommand;
import com.example.customeraccounts.application.command.CreateBankAccountCommand;
import com.example.customeraccounts.application.command.NewCustomerCommand;
import com.example.customeraccounts.domain.model.Customer;
import com.example.customeraccounts.domain.model.BankAccount;
import com.example.customeraccounts.infrastructure.web.dto.UpdateBankAccountBalanceRequest;
import com.example.customeraccounts.infrastructure.web.dto.CustomerResponse;
import com.example.customeraccounts.infrastructure.web.dto.CreateBankAccountRequest;
import com.example.customeraccounts.infrastructure.web.dto.BankAccountResponse;
import com.example.customeraccounts.infrastructure.web.dto.NewCustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountRestMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getNationalId(),
                customer.getFirstName(),
                customer.getFirstSurname(),
                customer.getSecondSurname(),
                customer.getBirthDate(),
                customer.getBankAccounts().stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    public BankAccountResponse toResponse(BankAccount bankAccount) {
        return new BankAccountResponse(bankAccount.getId(), bankAccount.getCustomerNationalId(), bankAccount.getAccountType(), bankAccount.getBalance());
    }

    public CreateBankAccountCommand toCommand(CreateBankAccountRequest request) {
        return new CreateBankAccountCommand(
                request.customerNationalId(),
                request.accountType(),
                request.balance(),
                java.util.Optional.ofNullable(request.customer()).map(this::toCommand).orElse(null)
        );
    }

    public UpdateBankAccountBalanceCommand toCommand(UpdateBankAccountBalanceRequest request) {
        return new UpdateBankAccountBalanceCommand(request.balance());
    }

    private NewCustomerCommand toCommand(NewCustomerRequest request) {
        return new NewCustomerCommand(
                request.firstName(),
                request.firstSurname(),
                request.secondSurname(),
                request.birthDate()
        );
    }
}


