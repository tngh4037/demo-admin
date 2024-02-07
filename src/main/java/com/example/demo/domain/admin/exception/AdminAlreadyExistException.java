package com.example.demo.domain.admin.exception;

import com.example.demo.global.error.exception.BusinessException;

public class AdminAlreadyExistException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "이미 존재하는 관리자 계정입니다.";

    public AdminAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public AdminAlreadyExistException(String message) {
        super(message);
    }

    public AdminAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}