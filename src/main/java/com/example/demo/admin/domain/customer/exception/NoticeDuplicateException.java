package com.example.demo.admin.domain.customer.exception;

import com.example.demo.admin.global.error.exception.InvalidValueException;

public class NoticeDuplicateException extends InvalidValueException {
    private final static String DEFAULT_MESSAGE = "중복된 공지글이 존재합니다. 확인 후 다시 시도해 주세요.";

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