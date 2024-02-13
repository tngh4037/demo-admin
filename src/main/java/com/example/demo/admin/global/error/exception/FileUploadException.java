package com.example.demo.admin.global.error.exception;

public class FileUploadException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "파일 업로드 처리에 실패했습니다.";

    public FileUploadException() {
        super(DEFAULT_MESSAGE);
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}