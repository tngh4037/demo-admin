package com.example.demo.admin.global.error.exception;

public class DataNotFoundException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 데이터 입니다.";

    public DataNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}