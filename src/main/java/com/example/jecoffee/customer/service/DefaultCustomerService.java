package com.example.jecoffee.customer.service;

import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.customer.repository.CustomerRepository;
import com.example.jecoffee.customer.exception.CustomerException;
import com.example.jecoffee.customer.exception.CustomerExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DefaultCustomerService.class);

    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Email email) {
        if (!checkDuplicatedEmail(email)) {
            return customerRepository.insert(Customer.createWithEmail(email));
        }
        logger.error(CustomerExceptionCode.DUPLICATED_EMAIL.getMessage());
        throw new CustomerException(CustomerExceptionCode.DUPLICATED_EMAIL);
    }

    private boolean checkDuplicatedEmail(Email email) {
        return customerRepository.findByEmail(email).isPresent();
    }
}
