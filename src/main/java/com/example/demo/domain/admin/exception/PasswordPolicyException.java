package com.example.demo.domain.admin.exception;

import com.example.demo.global.error.exception.BusinessException;

public class PasswordPolicyException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "패스워드 정책에 위반됩니다. 다시 확인해 주세요.";

    public PasswordPolicyException() {
        super(DEFAULT_MESSAGE);
    }

    public PasswordPolicyException(String message) {
        super(message);
    }

    public PasswordPolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}
