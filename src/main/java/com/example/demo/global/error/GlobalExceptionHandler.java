package com.example.demo.global.error;

import com.example.demo.global.error.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * [ 공통 예외 처리 ]
 * - 정의한 예외(=예측 가능한 예외)의 경우, 예외 발생시 사용자에게 정상적으로 응답한다. ( Exception 제외 )
 * - 정의한 예외(=예측 가능한 예외)가 아닌 경우, 오류 페이지를 리턴하고, 개발자 오류 알람(slack, email, sms)을 전송한다.
 * 
 * [ 응답 유형 ]
 * - Ajax or Accept Header('application/json') 요청의 경우 => json 형태로 응답
 * - 그 외 요청의 경우 => 오류 페이지(html) 응답
 * - (참고) 정의한 예외(=예측 가능한 예외)가 아닌 경우는 모두 오류 페이지(html) 응답
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final String ERROR_PAGE = "error";
	private static final String AJAX_HEADER_NAME = "X-Requested-With";
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

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

	private ModelAndView errorJson(String resMsg) {
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("resCd", HttpStatus.BAD_REQUEST.value());
		mav.addObject("resMsg", resMsg);
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