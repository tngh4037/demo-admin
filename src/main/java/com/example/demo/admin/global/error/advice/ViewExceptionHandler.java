package com.example.demo.admin.global.error.advice;

import com.example.demo.admin.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * HTML 화면 제공 처리 과정에서의 예외는 ViewExceptionHandler(@ControllerAdvice)에서 응답 처리한다.
 * 단, ViewExceptionHandler 에서 다루지 않는 예외의 경우는 스프링 부트 기본 오류 처리 매커니즘(BasicErrorController)를 따른다.
 *
 * [참고]
 * - ViewExceptionHandler 에서는 내부 비즈니스 예외 발생(BusinessException)의 경우에 한해서 정상적으로 응답 처리되도록 한다. 응답 처리 과정은 다음과 같다.
 *  ㄴ 응답 처리: 내부 redirct 페이지로 이동하여 예외 메시지를 alert으로 출력 후, 메인 페이지로 이동한다.
 */
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ViewExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ModelAndView handleBusinessException(BusinessException e) {
        log.info("handleBusinessException", e);
        return errorHtml(e.getMessage());
    }

    private ModelAndView errorHtml(String msg) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", msg);
        mav.addObject("url", "/");
        mav.setViewName("/common/redirect");
        return mav;
    }
}