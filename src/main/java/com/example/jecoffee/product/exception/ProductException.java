package com.example.jecoffee.product.exception;

public class ProductException extends RuntimeException {
    private String message;

    public ProductException(ProductExceptionCode productExceptionCode) {
        this.message = productExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
