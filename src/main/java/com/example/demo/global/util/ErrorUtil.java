package com.example.demo.global.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ErrorUtil {

    private static final String ERROR_BINDING_FAILURE_MESSAGE = "잘못된 요청입니다.";

    /**
     * BindingResult 오류 메시지 조회
     *
     * ex)
     * 검색 화면 등에서 사용자의 요청값이 유효하지 않아 오류 메시지를 alert 으로 노출할 때, 가장 첫 번째 오류의 원인에 해당하는 오류메시지를 리턴한다.
     * 이때, 첫 번째 오류의 원인이 타입 오류 등으로 인한 바인딩 실패인 경우는, 오류메시지로 ERROR_BINDING_FAILURE_MESSAGE 를 리턴한다.
     */
    public static String getBindingMessage(BindingResult bindingResult) {
        if (CommonUtil.isEmpty(bindingResult.getAllErrors())) {
            return null;
        }

        ObjectError error = bindingResult.getAllErrors().get(0);
        if (error instanceof FieldError && ((FieldError) error).isBindingFailure()) {
            return ERROR_BINDING_FAILURE_MESSAGE;
        }

        return error.getDefaultMessage();
    }
}
