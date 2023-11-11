package com.example.jecoffee.customer.service;

import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;


    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Email email) {
        if (!checkDuplicatedEmail(email)) {
            return customerRepository.insert(Customer.createWithEmail(email));
        }
    }

    private boolean checkDuplicatedEmail(Email email) {
        return customerRepository.findByEmail(email).isPresent();
    }
}
