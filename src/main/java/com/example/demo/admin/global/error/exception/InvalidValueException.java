package com.example.demo.admin.global.error.exception;

public class InvalidValueException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "유효하지 않은 값입니다.";

    public InvalidValueException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }
}