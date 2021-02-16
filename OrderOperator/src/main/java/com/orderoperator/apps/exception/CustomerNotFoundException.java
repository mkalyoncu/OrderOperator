package com.orderoperator.apps.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Integer id) {
        super("Could not find customer entity by id: " + id);
    }
}
