package com.example.jecoffee.customer.controller;

import com.example.jecoffee.customer.controller.dto.CreateCustomerRequest;
import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("고객 등록 성공")
    void Success_CreateCustomer() throws Exception {
        // given
        Email email = new Email("test@case.com");
        Customer customer = Customer.createWithEmail(email);
        when(customerService.createCustomer(email)).thenReturn(customer);

        // when, then
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CreateCustomerRequest(email.getAddress()))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("고객이 성공적으로 등록되었습니다."));
    }
}