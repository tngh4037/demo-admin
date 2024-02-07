package com.example.demo.domain.customer.exception;

import com.example.demo.global.error.exception.BusinessException;

public class FaqPolicyException extends BusinessException {
    private final static String DEFAULT_MESSAGE = "자주하는질문 정책에 위반됩니다. 확인 후 다시 시도해 주세요.";

    public FaqPolicyException() {
        super(DEFAULT_MESSAGE);
    }

    public FaqPolicyException(String message) {
        super(message);
    }

    public FaqPolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}