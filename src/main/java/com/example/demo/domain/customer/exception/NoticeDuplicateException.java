package com.example.demo.domain.customer.exception;

import com.example.demo.global.error.exception.BusinessException;

public class NoticeDuplicateException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "중복된 공지글이 존재합니다.";

    public NoticeDuplicateException() {
        super(DEFAULT_MESSAGE);
    }

    public NoticeDuplicateException(String message) {
        super(message);
    }

    public NoticeDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}