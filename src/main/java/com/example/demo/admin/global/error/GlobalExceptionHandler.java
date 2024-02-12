package com.example.demo.admin.global.error;

import com.example.demo.admin.global.error.exception.BusinessException;
import com.example.demo.admin.global.util.ErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// TODO :: 리팩토링
//  ㄴ 1) 뷰 / API 응답 분리 처리 고민 (뷰 처리시 4xx, 5xx 오류 페이지 별도 적용)
//  ㄴ 2) 예외 처리 정책 변경 (응답 포맷, 전체 오류 발생 컬럼 및 메시지 정보 추가 등) )
/**
 * [공통 예외 처리]
 * - 정의한 예외(=예측 가능한 예외)의 경우, 예외 발생시 클라이언트에 정상적으로 응답하고 에러 정보를 리턴한다. ( Exception 제외 )
 * - 정의한 예외(=예측 가능한 예외)가 아닌 경우, 오류 페이지(html)를 리턴하고, 개발자 오류 알람(slack, email, sms)을 전송한다.
 *
 * [요청 방식에 따른 응답 유형]
 * - Ajax or Accept Header('application/json') 요청의 경우 : json 형태로 응답한다.
 * - 그 외 요청의 경우 : 오류 페이지(error.html)를 응답한다.
 * - 단, 정의한 예외(=예측 가능한 예외)가 아닌 경우는, 요청 유형에 상관없이 모두 오류 페이지(html)를 응답한다.
 *
 * [(참고) 예외 발생시 json 응답 형태 예시는 아래와 같다.]
 * {
 *     code: 내부 결과 코드 ( ex> 400 )
 *     message: 결과 메시지 ( ex> "잘못된 요청입니다.", "질문을 입력해 주세요." ... )
 * }
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_PAGE = "error";
    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ModelAndView handleMethodArgumentNotValidException(HttpServletRequest request,
                                                               MethodArgumentNotValidException ex) {
        log.info("handleMethodArgumentNotValidException :: ", ex);
        return sendError(request, ErrorUtil.getBindingMessage(ex.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    private ModelAndView handleBindException(HttpServletRequest request, BindException ex) {
        log.error("handleBindException", ex);
        return sendError(request, ErrorUtil.getBindingMessage(ex.getBindingResult()));
    }

    @ExceptionHandler(BusinessException.class)
    private ModelAndView handleBusinessException(HttpServletRequest request, BusinessException ex) {
        log.info("handleBusinessException :: ", ex);
        return sendError(request, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    private String handleException(Exception ex) {
        log.error("handleException :: ", ex);
        // TODO :: 개발자 오류 알람 전송
        return ERROR_PAGE;
    }

    private ModelAndView sendError(HttpServletRequest request, String msg) {
        if (isAjaxRequest(request)) {
            return errorJson(msg);
        }
        return errorHtml(msg);
    }

    private ModelAndView errorJson(String message) {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("code", HttpStatus.BAD_REQUEST.value());
        mav.addObject("message", message);
        return mav;
    }

    private ModelAndView errorHtml(String msg) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", msg);
        mav.addObject("url", "/");
        mav.setViewName("/common/redirect");
        return mav;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader(AJAX_HEADER_NAME);
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        return AJAX_HEADER_VALUE.equals(header) || MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader);
    }
}