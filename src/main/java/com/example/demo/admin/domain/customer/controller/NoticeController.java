package com.example.demo.admin.domain.customer.controller;

import com.example.demo.admin.global.common.constant.PageConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.domain.customer.domain.Notice;
import com.example.demo.admin.domain.customer.dto.NoticeAddDto;
import com.example.demo.admin.domain.customer.dto.NoticeEditDto;
import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;
import com.example.demo.admin.domain.customer.service.NoticeService;
import com.example.demo.admin.domain.customer.validator.NoticeSearchValidator;
import com.example.demo.admin.global.util.ErrorUtil;
import com.example.demo.admin.global.common.PaginationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * [공지 사항]
 * 1) URI 구조
 * - 목록 조회: GET  /customer/notices
 * - 상세 조회: GET  /customer/notices/{id}
 * - 등록 화면: GET  /customer/notices/add
 * - 등록 처리: POST /customer/notices/add
 * - 수정 화면: GET  /customer/notices/{id}/edit
 * - 수정 처리: POST /customer/notices/{id}/edit
 * - 삭제 처리: POST /customer/notices/remove
 *   ㄴ 삭제 대상 정보는 form data 형태로 메시지 바디에 담아서 요청
 *
 * 2) 참고
 * - (BindingResult / BeanValidation)을 통한 client 요청 값 검증 및 redirect 처리
 * - PRG(Post-Redirect-Get) 방식을 통한 (저장 / 수정 / 삭제)
 */
@Slf4j
@Controller
@RequestMapping("/customer/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeSearchValidator noticeSearchValidator;

    /**
     * 목록 조회
     */
    @GetMapping
    public String list(@Validated @ModelAttribute("searchDto") NoticeSearchDto noticeSearchDto,
                       BindingResult bindingResult,
                       Model model) {

        noticeSearchValidator.validate(noticeSearchDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("msg", ErrorUtil.getBindingMessage(bindingResult));
            model.addAttribute("url", "/customer/notices");
            return ViewConstant.COMMON_REDIRECT;
        }

        PaginationDto paginationDto = new PaginationDto(noticeSearchDto.getPageNo(),
                PageConstant.COMMON_RECORD_COUNT, PageConstant.COMMON_PAGE_SIZE);
        List<Notice> noticeList = noticeService.findItems(noticeSearchDto, paginationDto);

        model.addAttribute("totalCount", paginationDto.getTotalRecordCount());
        model.addAttribute("paginationDto", paginationDto);
        model.addAttribute("noticeList", noticeList);

        return ViewConstant.CUSTOMER_NOTICE_LIST;
    }

    /**
     * 상세 조회
     */
    @GetMapping("/{noticeNo}")
    public String detail(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo));
        return ViewConstant.CUSTOMER_NOTICE_DETAIL;
    }

    /**
     * 등록 화면
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("noticeAddDto", NoticeAddDto.initForm());
        return ViewConstant.CUSTOMER_NOTICE_ADD_FORM;
    }

    /**
     * 등록 처리
     */
    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("noticeAddDto") NoticeAddDto noticeAddDto,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ViewConstant.CUSTOMER_NOTICE_ADD_FORM;
        }

        Notice notice = noticeService.save(noticeAddDto);
        redirectAttributes.addAttribute("noticeNo", notice.getNoticeNo());

        return "redirect:/customer/notices/{noticeNo}";
    }

    /**
     * 수정 화면
     */
    @GetMapping("/{noticeNo}/edit")
    public String editForm(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo));
        return ViewConstant.CUSTOMER_NOTICE_EDIT_FORM;
    }

    /**
     * 수정 처리
     */
    @PostMapping("/{noticeNo}/edit")
    public String edit(@PathVariable("noticeNo") Integer noticeNo,
                       @Validated @ModelAttribute("notice") NoticeEditDto noticeEditDto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ViewConstant.CUSTOMER_NOTICE_EDIT_FORM;
        }

        noticeService.update(noticeNo, noticeEditDto);
        return "redirect:/customer/notices/{noticeNo}";
    }

    /**
     * 삭제 처리
     */
    @PostMapping("/remove")
    public String remove(@RequestParam("noticeNos") Integer[] noticeNos,
                         @RequestParam(value = "searchParams", required = false, defaultValue = "") String searchParams) {
        noticeService.remove(noticeNos);
        return "redirect:/customer/notices" + searchParams;
    }
}