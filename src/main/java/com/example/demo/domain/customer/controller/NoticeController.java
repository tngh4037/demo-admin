package com.example.demo.domain.customer.controller;

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

    /*
    // DML 처리는 1차적으로 PRG(Post/Redirect/Get) 방식으로 해두기
    @GetMapping("/detail/{noticeNo}")
    public String detail(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo).get());
        return "customer/noticeDetail";
    }

    @GetMapping("/register")
    public String detail() {
        model.addAttribute("notice", noticeService.findById(noticeNo).get());
        return "customer/noticeRegForm";
    }

    @PostMapping("/register")
    @ResponseBody
    public String detail(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo).get());
        return "customer/noticeDetail";
    }

    @GetMapping("/modify/{noticeNo}")
    public String detail(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo).get());
        return "customer/noticeDetail";
    }

    @PostMapping("/modify/{noticeNo}")
    @ResponseBody
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
