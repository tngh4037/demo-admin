package com.example.demo.admin.global.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO :: 리팩토링 ( 응답 포맷, naming )
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JsonResult<T> {

    private static final int SUCCESS = 200;

    private Integer code;
    private String message;
    private T data;

    public static JsonResult<?> ok() {
        return new JsonResult<>(SUCCESS, null, null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(SUCCESS, null, data);
    }

    public static <T> JsonResult<T> ok(String message, T data) {
        return new JsonResult<>(SUCCESS, message, data);
    }

    private JsonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}