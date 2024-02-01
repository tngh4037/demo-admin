package com.example.demo.global.error.exception;

/**
 * business logic 최상위 예외 클래스
 */
public class BusinessException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "처리 중 오류가 발생했습니다.";

    public BusinessException() {
        super(DEFAULT_MESSAGE);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}