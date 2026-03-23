package com.example.common.exception;

public class UnprocessableEntityException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Unprocessable entity exception.";

    public UnprocessableEntityException() {
        super(DEFAULT_MESSAGE);
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}

