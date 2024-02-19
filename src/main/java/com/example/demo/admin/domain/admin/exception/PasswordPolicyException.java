package com.example.demo.admin.domain.admin.exception;

import com.example.demo.admin.global.error.exception.InvalidValueException;

public class PasswordPolicyException extends InvalidValueException {
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
