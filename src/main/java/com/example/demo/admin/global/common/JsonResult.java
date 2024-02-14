package com.example.demo.admin.global.common;

import lombok.Getter;

/**
 * JSON data 응답 시 일관된 포맷을 위한 객체
 */
@Getter
public class JsonResult<T> {
    private static final int SUCCESS = 200;
    private static final String DEFAULT_MESSAGE = "정상적으로 처리되었습니다.";

    private final Integer code;
    private final String message;
    private final T data;

    public static JsonResult<?> ok() {
        return new JsonResult<>(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(data);
    }

    private JsonResult(T data) {
        this.code = SUCCESS;
        this.message = DEFAULT_MESSAGE;
        this.data = data;
    }
}