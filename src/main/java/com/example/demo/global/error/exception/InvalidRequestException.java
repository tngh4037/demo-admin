package com.example.demo.global.error.exception;

public class InvalidRequestException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "유효하지 않은 요청입니다.";

    public InvalidRequestException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}