package com.example.jecoffee.customer.repository;

import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;

import java.util.Optional;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findByEmail(Email email);
}
