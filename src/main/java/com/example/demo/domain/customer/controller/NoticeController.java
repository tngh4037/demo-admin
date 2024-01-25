package com.example.demo.domain.customer.controller;

import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String list(@ModelAttribute("searchDto") NoticeSearchDto noticeSearchDto, Model model) {
        List<Notice> noticeList = noticeService.findItems(noticeSearchDto);
        model.addAttribute("totalCount", noticeList.size());
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("noticeTypeList", NoticeType.values());
        return "customer/noticeList";
    }

    /*
    @GetMapping("/detail/{noticeNo}")
    public String detail(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo).get());
        return "customer/noticeDetail";
    }

    @PostMapping("/remove")
    @ResponseBody
    public String remove(@RequestBody Integer[] noticeNoList) {
        noticeService.deleteByIds(noticeNoList);
        return "customer/noticeDetail";
    }
    */
}
