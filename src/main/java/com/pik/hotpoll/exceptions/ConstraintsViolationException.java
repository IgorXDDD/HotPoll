package com.pik.hotpoll.exceptions;

public class ConstraintsViolationException extends Exception {
    private String message;
    public ConstraintsViolationException(String message) {
        this.message = message;
    }
}
