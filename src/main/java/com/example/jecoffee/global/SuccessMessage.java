package com.example.jecoffee.global;

import org.springframework.http.HttpStatus;

public class SuccessMessage {

    private String message;
    private HttpStatus httpStatus;
    private Object data;

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Object getData() {
        return data;
    }

    public SuccessMessage(String message, HttpStatus httpStatus, Object data) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public SuccessMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
