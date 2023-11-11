package com.example.jecoffee.customer.exception;

public class CustomerException extends RuntimeException {
    private String message;

    public CustomerException(CustomerExceptionCode customerExceptionCode) {
        this.message = customerExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
