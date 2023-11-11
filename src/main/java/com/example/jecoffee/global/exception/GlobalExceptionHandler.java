package com.example.jecoffee.global.exception;

import com.example.jecoffee.customer.exception.CustomerException;
import com.example.jecoffee.exception.*;
import com.example.jecoffee.order.exception.OrderException;
import com.example.jecoffee.product.exception.ProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleCustomerException(CustomerException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleDataException(DataException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleOrderException(OrderException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleProductException(ProductException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
