package com.example.demo.admin.domain.customer.controller;

import com.example.demo.admin.global.common.constant.PageConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqSearchDto;
import com.example.demo.admin.domain.customer.service.FaqService;
import com.example.demo.admin.global.util.MessageHelper;
import com.example.demo.admin.global.common.PaginationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/customer/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @GetMapping
    public String list(@Validated @ModelAttribute("searchDto") FaqSearchDto faqSearchDto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("msg", MessageHelper.getBindingErrorMessage(bindingResult));
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

    @GetMapping("/{faqNo}")
    public String detailForm(@PathVariable("faqNo") Integer faqNo, Model model) {
        model.addAttribute("faq", faqService.findById(faqNo));
        return ViewConstant.CUSTOMER_FAQ_DETAIL;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("faq", FaqAddDto.initForm());
        return ViewConstant.CUSTOMER_FAQ_ADD_FORM;
    }

    @GetMapping("/{faqNo}/edit")
    public String editForm(@PathVariable("faqNo") Integer faqNo, Model model) {
        model.addAttribute("faq", faqService.findById(faqNo));
        return ViewConstant.CUSTOMER_FAQ_EDIT_FORM;
    }
}