package com.example.customeraccounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerAccountsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCustomersReturnsInitialCustomersWithBankAccounts() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[*].dni", containsInAnyOrder(
                        "11111111A", "22222222B", "33333333C", "44444444D", "55555555E"
                )))
                .andExpect(jsonPath("$[0].cuentas", hasSize(2)));
    }

    @Test
    void getAdultCustomersReturnsOnlyAdultCustomers() throws Exception {
        mockMvc.perform(get("/clientes/mayores-de-edad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].dni", containsInAnyOrder(
                        "11111111A", "22222222B", "44444444D", "55555555E"
                )));
    }

    @Test
    void getCustomersWithBalanceGreaterThanFiltersByTotalBalance() throws Exception {
        mockMvc.perform(get("/clientes/con-cuenta-superior-a/{amount}", 100000))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].dni", containsInAnyOrder("11111111A", "55555555E")));
    }

    @Test
    void postBankAccountsCreatesAccountForExistingCustomer() throws Exception {
        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dniCliente": "11111111A",
                                  "tipoCuenta": "NORMAL",
                                  "total": 50000
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dniCliente", is("11111111A")))
                .andExpect(jsonPath("$.tipoCuenta", is("NORMAL")))
                .andExpect(jsonPath("$.total", is(50000)));

        mockMvc.perform(get("/clientes/{nationalId}", "11111111A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuentas", hasSize(3)));
    }

    @Test
    void postBankAccountsCreatesCustomerAutomaticallyWhenMissing() throws Exception {
        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dniCliente": "66666666F",
                                  "tipoCuenta": "PREMIUM",
                                  "total": 90000,
                                  "cliente": {
                                    "nombre": "Laura",
                                    "apellido1": "Garcia",
                                    "apellido2": "Martin",
                                    "fechaNacimiento": "1990-04-15"
                                  }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dniCliente", is("66666666F")));

        mockMvc.perform(get("/clientes/{nationalId}", "66666666F"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni", is("66666666F")))
                .andExpect(jsonPath("$.nombre", is("Laura")))
                .andExpect(jsonPath("$.apellido1", is("Garcia")))
                .andExpect(jsonPath("$.cuentas", hasSize(1)));
    }

    @Test
    void postBankAccountsAllowsMinimalCustomerWhenNoCustomerDataIsProvided() throws Exception {
        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dniCliente": "77777777G",
                                  "tipoCuenta": "normal",
                                  "total": 700
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoCuenta", is("NORMAL")));

        mockMvc.perform(get("/clientes/{nationalId}", "77777777G"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Unknown")))
                .andExpect(jsonPath("$.apellido1", is("No data")));
    }

    @Test
    void putBankAccountsUpdatesBalance() throws Exception {
        mockMvc.perform(put("/cuentas/{accountId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "total": 180000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.total", is(180000)));
    }

    @Test
    void getCustomerByNationalIdReturnsRequestedCustomer() throws Exception {
        mockMvc.perform(get("/clientes/{nationalId}", "22222222B"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni", is("22222222B")))
                .andExpect(jsonPath("$.nombre", is("Raul")))
                .andExpect(jsonPath("$.cuentas", hasSize(2)));
    }

    @Test
    void getCustomerByNationalIdReturns404WhenMissing() throws Exception {
        mockMvc.perform(get("/clientes/{nationalId}", "99999999Z"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    void putBankAccountsRejectsNegativeBalance() throws Exception {
        mockMvc.perform(put("/cuentas/{accountId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "total": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)));
    }
}


