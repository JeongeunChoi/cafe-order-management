package com.example.jecoffee.customer.controller;

import com.example.jecoffee.global.SuccessMessage;
import com.example.jecoffee.customer.controller.dto.CreateCustomerRequest;
import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<SuccessMessage> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerService.createCustomer(new Email(createCustomerRequest.getEmail()));

        return ResponseEntity.ok(
                new SuccessMessage("고객이 성공적으로 등록되었습니다."
                        , HttpStatus.CREATED, customer));
    }
}
