package com.example.demo.admin.domain.admin.exception;

import com.example.demo.admin.global.error.exception.InvalidValueException;

public class AdminDuplicateException extends InvalidValueException {
    private static final String DEFAULT_MESSAGE = "이미 존재하는 관리자 계정입니다.";

    public AdminDuplicateException() {
        super(DEFAULT_MESSAGE);
    }

    public AdminDuplicateException(String message) {
        super(message);
    }

    public AdminDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}