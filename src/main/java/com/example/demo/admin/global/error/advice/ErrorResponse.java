package com.example.demo.admin.global.error.advice;

import com.example.demo.admin.global.util.MessageHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API 예외 발생시 공통 응답 객체
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private static final String BAD_REQUEST_ERROR_MESSAGE = "잘못된 요청입니다.";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "처리 중 오류가 발생했습니다.";

    private int status;
    private String message;
    private List<FieldError> errors;

    private ErrorResponse(final int status, final String message, final List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse of(final int status) {
        String message = (status == HttpStatus.INTERNAL_SERVER_ERROR.value() ?
                INTERNAL_SERVER_ERROR_MESSAGE : BAD_REQUEST_ERROR_MESSAGE);
        return new ErrorResponse(status, message, new ArrayList<>());
    }

    public static ErrorResponse of(final int status, String message) {
        return new ErrorResponse(status, message, new ArrayList<>());
    }

    public static ErrorResponse of(final int status, final BindingResult bindingResult) {
        return new ErrorResponse(status, MessageHelper.getBindingErrorMessage(bindingResult), FieldError.of(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            MessageHelper.getBindingErrorMessage(error)))
                    .collect(Collectors.toList());
        }
    }
}
