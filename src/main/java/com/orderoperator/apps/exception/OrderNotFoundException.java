package com.orderoperator.apps.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Integer id) {
        super("Could not find order entity by id: " + id);
    }

    public OrderNotFoundException(String message) {
        super("Could not find order by " + message);
    }
}
