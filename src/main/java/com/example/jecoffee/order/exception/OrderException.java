package com.example.jecoffee.order.exception;

public class OrderException extends RuntimeException {
    private String message;

    public OrderException(OrderExceptionCode orderExceptionCode) {
        this.message = orderExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
