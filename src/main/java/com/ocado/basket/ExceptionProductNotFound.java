package com.ocado.basket;

public class ExceptionProductNotFound extends RuntimeException {
    public ExceptionProductNotFound(String exception) {
        super(exception);
    }
}
