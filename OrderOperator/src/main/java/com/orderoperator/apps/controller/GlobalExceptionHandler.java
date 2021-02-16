package com.orderoperator.apps.controller;

import com.orderoperator.apps.exception.CustomerNotFoundException;
import com.orderoperator.apps.exception.OrderNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final String customerNotFoundExceptionMessage;
    private final String orderNotFoundExceptionMessage;
    private final String unknownExceptionMessage;

    public GlobalExceptionHandler(@Value("${customer.not.found.exception}") String customerNotFoundExceptionMessage,
                                  @Value("${order.not.found.exception}") String orderNotFoundExceptionMessage,
                                  @Value("${unknown.exception}") String unknownExceptionMessage) {
        this.customerNotFoundExceptionMessage = customerNotFoundExceptionMessage;
        this.orderNotFoundExceptionMessage = orderNotFoundExceptionMessage;
        this.unknownExceptionMessage = unknownExceptionMessage;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = CustomerNotFoundException.class)
    public String handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return customerNotFoundExceptionMessage;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = OrderNotFoundException.class)
    public String handleOrderNotFoundException(CustomerNotFoundException exception) {
        return orderNotFoundExceptionMessage;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = Exception.class)
    public String handleUnknownException(CustomerNotFoundException exception) {
        return unknownExceptionMessage;
    }
}
