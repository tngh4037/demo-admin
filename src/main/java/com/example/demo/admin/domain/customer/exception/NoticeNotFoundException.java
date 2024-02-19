package com.example.demo.admin.domain.customer.exception;

import com.example.demo.admin.global.error.exception.DataNotFoundException;

public class NoticeNotFoundException extends DataNotFoundException {
    private final static String DEFAULT_MESSAGE = "공지사항 게시글이 존재하지 않습니다.";

    public NoticeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NoticeNotFoundException(String message) {
        super(message);
    }

    public NoticeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}