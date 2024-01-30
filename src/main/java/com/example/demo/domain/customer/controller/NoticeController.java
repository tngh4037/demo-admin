package com.example.demo.domain.customer.controller;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeAddDto;
import com.example.demo.domain.customer.dto.NoticeEditDto;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.service.NoticeService;
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
 *
 * 2) 참고
 * - PRG(Post-Redirect-Get) 방식을 통한 (저장 / 수정)
 * - (BindingResult / BeanValidation)을 통한 client 요청 값 검증 처리
 */
@Controller
@RequestMapping("/customer/notices")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 목록 조회
     */
    @GetMapping
    public String list(@ModelAttribute("searchDto") NoticeSearchDto noticeSearchDto, Model model) {
        if (noticeSearchDto.getPageNo() < 1) {
            noticeSearchDto.setPageNo(1);
        }

        noticeSearchDto.setRecordCount(10);
        noticeSearchDto.setPageSize(10);

        List<Notice> noticeList = noticeService.findItems(noticeSearchDto);
        model.addAttribute("totalCount", noticeSearchDto.getTotalRecordCount());
        model.addAttribute("noticeList", noticeList);

        return "customer/noticeList";
    }

    /**
     * 상세 조회
     */
    @GetMapping("/{noticeNo}")
    public String detail(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo));
        return "customer/noticeDetail";
    }

    /**
     * 등록 화면
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("noticeAddDto", new NoticeAddDto());
        return "customer/noticeAddForm";
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
            return "customer/noticeAddForm";
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
        return "customer/noticeEditForm";
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
            return "customer/noticeEditForm";
        }

        noticeService.update(noticeNo, noticeEditDto);
        return "redirect:/customer/notices/{noticeNo}";
    }
}
