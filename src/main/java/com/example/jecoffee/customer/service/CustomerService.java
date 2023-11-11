package com.example.jecoffee.customer.service;

import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;

public interface CustomerService {

    Customer createCustomer(Email email);
}
