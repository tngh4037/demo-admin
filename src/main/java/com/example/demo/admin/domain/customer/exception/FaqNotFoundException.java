package com.example.demo.admin.domain.customer.exception;

import com.example.demo.admin.global.error.exception.DataNotFoundException;

public class FaqNotFoundException extends DataNotFoundException {
    private final static String DEFAULT_MESSAGE = "자주하는질문 게시글이 존재하지 않습니다.";

    public FaqNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public FaqNotFoundException(String message) {
        super(message);
    }

    public FaqNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}