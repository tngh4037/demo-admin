package com.example.demo.admin.domain.customer.exception;

import com.example.demo.admin.global.error.exception.BusinessException;

public class NoticeFileException extends BusinessException {
    private final static String DEFAULT_MESSAGE = "공지사항 파일 처리에 실패했습니다. 확인 후 다시 시도해 주세요.";

    public NoticeFileException() {
        super(DEFAULT_MESSAGE);
    }

    public NoticeFileException(String message) {
        super(message);
    }

    public NoticeFileException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public NoticeFileException(String message, Throwable cause) {
        super(message, cause);
    }
}