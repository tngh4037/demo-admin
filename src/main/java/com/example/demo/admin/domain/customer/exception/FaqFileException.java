package com.example.demo.admin.domain.customer.exception;

import com.example.demo.admin.global.error.exception.UploadFileException;

public class FaqFileException extends UploadFileException {
    private final static String DEFAULT_MESSAGE = "자주하는질문 파일 처리에 실패했습니다.";

    public FaqFileException() {
        super(DEFAULT_MESSAGE);
    }

    public FaqFileException(String message) {
        super(message);
    }

    public FaqFileException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public FaqFileException(String message, Throwable cause) {
        super(message, cause);
    }
}