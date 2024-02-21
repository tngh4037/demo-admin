package com.example.demo.admin.global.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Component
public class MessageHelper {

    private static MessageSource messageSource;

    private MessageHelper(final MessageSource source) {
        messageSource = source;
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * BindingResult 오류 메시지 조회
     *
     * ex) 클라이언트의 요청 값이 유효하지 않아 오류 메시지를 전달할 때, 가장 첫 번째 오류의 원인에 해당하는 오류 메시지를 전달한다.
     * 이때, 첫 번째 오류의 원인이
     * - (타입 오류 등으로 인한 바인딩 실패인 경우) 메세지 프로퍼티에 정의된 "typeMismatch"에 해당하는 메세지를 리턴한다.
     * - (타입 오류 등으로 인한 바인딩 실패가 아닌 경우) 첫 번쨰 오류에 해당하는 오류 코드를 메세지 프로퍼티에서 찾아 조회한다. ( 만약, 메시지 프로퍼티에 오류 코드가 정의되어 있지 않은 경우, 기본 default 메시지를 리턴한다. )
     */
    public static String getBindingErrorMessage(BindingResult bindingResult) {
        if (CommonUtil.isEmpty(bindingResult.getAllErrors())) {
            return null;
        }

        return getBindingErrorMessage(bindingResult.getAllErrors().get(0));
    }

    public static String getBindingErrorMessage(ObjectError error) {
        if (error instanceof FieldError && ((FieldError) error).isBindingFailure()) {
            return getMessage("typeMismatch");
        }

        return getMessage(error.getCode(), error.getArguments(), error.getDefaultMessage());
    }
}
