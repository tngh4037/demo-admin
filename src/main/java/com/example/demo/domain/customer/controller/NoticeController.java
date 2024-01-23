package com.example.demo.domain.customer.controller;

import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("totalCount", noticeService.getList().size());
        model.addAttribute("noticeList", noticeService.getList());
        model.addAttribute("noticeTypeList", NoticeType.values());
        return "customer/noticeList";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(value = "noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("noticeDetail", noticeService.getDetail(noticeNo));
        return "customer/noticeDetail";
    }
}
