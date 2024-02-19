package com.example.demo.admin.domain.customer.exception;

import com.example.demo.admin.global.error.exception.DataNotFoundException;

public class NoticeFileNotFoundException extends DataNotFoundException {
    private final static String DEFAULT_MESSAGE = "공지사항 파일 정보가 존재하지 않습니다.";

    public NoticeFileNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NoticeFileNotFoundException(String message) {
        super(message);
    }

    public NoticeFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}