package com.example.jecoffee.customer.controller.dto;

public class CreateCustomerRequest {
    private String email;

    public CreateCustomerRequest() {
    }

    public CreateCustomerRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "CreateCustomerRequest{" +
                "email='" + email + '\'' +
                '}';
    }
}

