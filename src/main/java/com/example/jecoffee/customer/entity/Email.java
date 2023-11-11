package com.example.jecoffee.customer.entity;

import com.example.jecoffee.customer.exception.CustomerException;
import com.example.jecoffee.customer.exception.CustomerExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class Email {

    private String address;

    private static final Logger logger = LoggerFactory.getLogger(Email.class);

    public Email(String address) {
        if (checkAddressLength(address) && checkAddressFormat(address)) {
            this.address = address;
        }
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    private boolean checkAddressLength(String address) {
        if (address.length() >= 4 && address.length() <= 50) {
            return true;
        } else {
            logger.error(CustomerExceptionCode.INVALID_EMAIL_LENGTH.getMessage());
            throw new CustomerException(CustomerExceptionCode.INVALID_EMAIL_LENGTH);
        }
    }

    private boolean checkAddressFormat(String address) {
        if (Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address)) {
            return true;
        } else {
            logger.error(CustomerExceptionCode.INVALID_EMAIL_FORMAT.getMessage());
            throw new CustomerException(CustomerExceptionCode.INVALID_EMAIL_FORMAT);
        }
    }
}
