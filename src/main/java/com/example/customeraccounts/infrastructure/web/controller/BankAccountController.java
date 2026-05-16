package com.example.customeraccounts.infrastructure.web.controller;

import com.example.customeraccounts.application.service.CustomerAccountService;
import com.example.customeraccounts.infrastructure.web.dto.BankAccountResponse;
import com.example.customeraccounts.infrastructure.web.dto.CreateBankAccountRequest;
import com.example.customeraccounts.infrastructure.web.dto.UpdateBankAccountBalanceRequest;
import com.example.customeraccounts.infrastructure.web.mapper.CustomerAccountRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuentas")
@Tag(name = "Bank accounts", description = "Bank account creation and balance update operations")
public class BankAccountController {

    private final CustomerAccountService service;
    private final CustomerAccountRestMapper mapper;

    public BankAccountController(CustomerAccountService service, CustomerAccountRestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new bank account",
            description = "If the customer exists, the account is associated with it. Otherwise, the customer is created automatically.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Bank account created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    public BankAccountResponse createBankAccount(@Valid @RequestBody CreateBankAccountRequest request) {
        return mapper.toResponse(service.createBankAccount(mapper.toCommand(request)));
    }

    @PutMapping("/{accountId}")
    @Operation(
            summary = "Update bank account balance",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bank account updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Bank account not found")
            }
    )
    public BankAccountResponse updateBalance(@PathVariable Long accountId,
                                                  @Valid @RequestBody UpdateBankAccountBalanceRequest request) {
        return mapper.toResponse(service.updateBankAccountBalance(accountId, mapper.toCommand(request)));
    }
}


