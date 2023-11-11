package com.example.jecoffee.customer.entity;

import java.util.UUID;

public class Customer {

    UUID customerId;
    private final Email email;

    public static Customer createWithEmail(Email email) {
        return new Customer(email);
    }

    public static Customer createWithCustomerIdAndEmail(UUID customerId, Email email) {
        return new Customer(customerId, email);
    }

    private Customer(UUID customerId, Email email) {
        this.customerId = customerId;
        this.email = email;
    }

    private Customer(Email email) {
        this.customerId = UUID.randomUUID();
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Email getEmail() {
        return email;
    }
}
