package com.example.jecoffee.customer.entity;

import com.example.jecoffee.customer.exception.CustomerException;
import com.example.jecoffee.customer.exception.CustomerExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class Email {

    private String address;


    public Email(String address) {
            this.address = address;
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

}
