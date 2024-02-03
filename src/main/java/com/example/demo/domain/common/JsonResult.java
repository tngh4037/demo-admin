package com.example.demo.domain.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO :: 리팩토링 ( 응답 포맷, naming )
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JsonResult<T> {

    private static final int SUCCESS = 200;

    private int code;
    private String message;
    private T data;

    public static JsonResult<?> ok() {
        return new JsonResult<>(SUCCESS, null, null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(SUCCESS, data, null);
    }

    public static <T> JsonResult<T> ok(T data, String message) {
        return new JsonResult<>(SUCCESS, data, message);
    }

    private JsonResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}