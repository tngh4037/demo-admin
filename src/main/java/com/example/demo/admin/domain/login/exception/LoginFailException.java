package com.example.demo.admin.domain.login.exception;

import com.example.demo.admin.global.error.exception.BusinessException;

public class LoginFailException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "로그인에 실패했습니다. 다시 시도해 주세요.";

    public LoginFailException() {
        super(DEFAULT_MESSAGE);
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }
}