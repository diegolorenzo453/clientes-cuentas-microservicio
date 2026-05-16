package com.example.customeraccounts.infrastructure.web.controller;

import com.example.customeraccounts.application.service.CustomerAccountService;
import com.example.customeraccounts.domain.model.Customer;
import com.example.customeraccounts.infrastructure.web.dto.CustomerResponse;
import com.example.customeraccounts.infrastructure.web.mapper.CustomerAccountRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Customers", description = "Customer query and filtering operations")
public class CustomerController {

    private final CustomerAccountService service;
    private final CustomerAccountRestMapper mapper;

    public CustomerController(CustomerAccountService service, CustomerAccountRestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(
            summary = "Find all customers with their bank accounts",
            responses = @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    )
    public List<CustomerResponse> findCustomers() {
        return toResponseList(service.findCustomersWithAccounts());
    }

    @GetMapping("/mayores-de-edad")
    @Operation(summary = "Find adult customers")
    public List<CustomerResponse> findAdultCustomers() {
        return toResponseList(service.findAdultCustomers());
    }

    @GetMapping("/con-cuenta-superior-a/{amount}")
    @Operation(summary = "Find customers whose total account balance is greater than the given amount")
    public List<CustomerResponse> findCustomersWithTotalBalanceGreaterThan(@PathVariable BigDecimal amount) {
        return toResponseList(service.findCustomersWithTotalBalanceGreaterThan(amount));
    }

    @GetMapping("/{nationalId}")
    @Operation(
            summary = "Find customer by national id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    public CustomerResponse findCustomerByNationalId(@PathVariable String nationalId) {
        return mapper.toResponse(service.findCustomerByNationalId(nationalId));
    }

    private List<CustomerResponse> toResponseList(List<Customer> customers) {
        return customers.stream()
                .map(mapper::toResponse)
                .toList();
    }
}


