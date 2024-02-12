package com.example.demo.admin.domain.customer.controller;

import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.ValidationSequence;
import com.example.demo.admin.global.common.constant.PageConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.dto.FaqSearchDto;
import com.example.demo.admin.domain.customer.service.FaqService;
import com.example.demo.admin.global.util.ErrorUtil;
import com.example.demo.admin.global.common.PaginationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [자주하는질문]
 * 1) URI 구조
 * - 목록 조회: GET  /customer/faqs
 * - 상세 조회: GET  /customer/faqs/{id}
 * - 등록 화면: GET  /customer/faqs/add
 * - 등록 처리: POST /customer/faqs/add
 * - 수정 화면: GET  /customer/faqs/{id}/edit
 * - 수정 처리: POST /customer/faqs/{id}/edit
 * - 삭제 처리: POST /customer/faqs/remove
 *   ㄴ 삭제 대상 정보는 json 형태로 메시지 바디에 담아서 요청
 *
 * 2) 참고
 * - (BindingResult / BeanValidation)을 통한 client 요청 값 검증 및 redirect 처리
 * - 웹 클라이언트(Ajax)를 통한 (저장 / 수정 / 삭제)
 *   ㄴ 요청 / 응답 형태 : json
 */
@Slf4j
@Controller
@RequestMapping("/customer/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    /**
     * 목록 조회
     */
    @GetMapping
    public String list(@Validated @ModelAttribute("searchDto") FaqSearchDto faqSearchDto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("msg", ErrorUtil.getBindingMessage(bindingResult));
            model.addAttribute("url", "/customer/faqs");
            return ViewConstant.COMMON_REDIRECT;
        }

        PaginationDto paginationDto = new PaginationDto(faqSearchDto.getPageNo(),
                PageConstant.COMMON_RECORD_COUNT, PageConstant.COMMON_PAGE_SIZE);
        List<Faq> faqList = faqService.findItems(faqSearchDto, paginationDto);

        model.addAttribute("totalCount", paginationDto.getTotalRecordCount());
        model.addAttribute("paginationDto", paginationDto);
        model.addAttribute("faqList", faqList);

        return ViewConstant.CUSTOMER_FAQ_LIST;
    }

    /**
     * 상세 조회
     */
    @GetMapping("/{faqNo}")
    public String detail(@PathVariable("faqNo") Integer faqNo, Model model) {
        model.addAttribute("faq", faqService.findById(faqNo));
        return ViewConstant.CUSTOMER_FAQ_DETAIL;
    }

    /**
     * 등록 화면
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("faq", FaqAddDto.initForm());
        return ViewConstant.CUSTOMER_FAQ_ADD_FORM;
    }

    /**
     * 등록 처리
     */
    @PostMapping("/add")
    @ResponseBody
    public JsonResult<?> add(@RequestBody @Validated(ValidationSequence.class) FaqAddDto faqAddDto) {
        faqService.save(faqAddDto);
        return JsonResult.ok();
    }

    /**
     * 수정 화면
     */
    @GetMapping("/{faqNo}/edit")
    public String editForm(@PathVariable("faqNo") Integer faqNo, Model model) {
        model.addAttribute("faq", faqService.findById(faqNo));
        return ViewConstant.CUSTOMER_FAQ_EDIT_FORM;
    }

    /**
     * 수정 처리
     */
    @PostMapping("/{faqNo}/edit")
    @ResponseBody
    public JsonResult<?> edit(@PathVariable("faqNo") Integer faqNo,
                              @RequestBody @Validated(ValidationSequence.class) FaqEditDto noticeEditDto) {
        faqService.update(faqNo, noticeEditDto);
        return JsonResult.ok();
    }

    /**
     * 삭제 처리
     */
    @PostMapping("/remove")
    @ResponseBody
    public JsonResult<?> remove(@RequestBody List<Integer> faqNos) {
        faqService.remove(faqNos);
        return JsonResult.ok();
    }
}