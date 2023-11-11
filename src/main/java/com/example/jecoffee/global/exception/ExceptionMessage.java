package com.example.jecoffee.global.exception;

import org.springframework.http.HttpStatus;

public class ExceptionMessage {
    private String message;
    private HttpStatus httpStatus;

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
