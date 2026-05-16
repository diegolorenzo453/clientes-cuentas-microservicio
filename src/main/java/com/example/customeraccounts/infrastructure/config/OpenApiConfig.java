package com.example.customeraccounts.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI bankingAccountsOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customers and Bank Accounts API")
                        .version("1.0.0")
                        .description("REST microservice with hexagonal architecture for managing customers and bank accounts.")
                        .contact(new Contact().name("Technical exercise")));
    }
}


