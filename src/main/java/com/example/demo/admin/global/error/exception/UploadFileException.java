package com.example.demo.admin.global.error.exception;

public class UploadFileException extends BusinessException {
    private static final String DEFAULT_MESSAGE = "파일 업로드 처리에 실패했습니다.";

    public UploadFileException() {
        super(DEFAULT_MESSAGE);
    }

    public UploadFileException(String message) {
        super(message);
    }

    public UploadFileException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}