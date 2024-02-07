package com.example.demo.domain.admin.exception;

import com.example.demo.global.error.exception.BusinessException;

public class AdminNotFoundException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "대상 관리자 계정이 존재하지 않습니다.";

    public AdminNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public AdminNotFoundException(String message) {
        super(message);
    }

    public AdminNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}