package com.example.demo.domain.customer.exception;

import com.example.demo.global.error.exception.BusinessException;

public class FaqException extends BusinessException {
    private final static String DEFAULT_MESSAGE = "자주하는질문 처리 중 오류가 발생했습니다. 다시 시도해 주세요.";

    public FaqException() {
        super(DEFAULT_MESSAGE);
    }

    public FaqException(String message) {
        super(message);
    }

    public FaqException(String message, Throwable cause) {
        super(message, cause);
    }
}