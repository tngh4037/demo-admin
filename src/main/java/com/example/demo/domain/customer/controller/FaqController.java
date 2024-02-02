package com.example.demo.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * [자주하는질문]
 * 1) URI 구조
 * - 목록 조회: GET  /customer/faqs
 * - 상세 조회: GET  /customer/faqs/{id}
 * - 등록 화면: GET  /customer/faqs/add
 * - 등록 처리: POST /customer/faqs/add
 * - 수정 화면: GET  /customer/faqs/{id}/edit
 * - 수정 처리: POST /customer/faqs/{id}/edit
 * - 수정 처리: POST /customer/faqs/{id}/edit/display
 * - 삭제 처리: POST /customer/faqs/remove
 *   ㄴ 삭제 대상 정보는 body 에 json 형태로 담아서 요청
 *
 * 2) 참고
 * - PRG(Post-Redirect-Get) 방식을 통한 (저장 / 수정 / 삭제)
 * - (BindingResult / BeanValidation 을 통한 client 요청 값 검증 및 redirect 처리
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FaqController {

}
