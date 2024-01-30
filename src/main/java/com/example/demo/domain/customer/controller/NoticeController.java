package com.example.demo.domain.customer.controller;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeAddDto;
import com.example.demo.domain.customer.dto.NoticeEditDto;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * [공지 사항]
 * - (참고) HTTP Method 는 GET/POST를 사용하며, URL는 다음과 같이 분류한다. (컨트롤 URI 사용)
 * - 목록 조회: GET  /customer/notices
 * - 상세 조회: GET  /customer/notices/{id}
 * - 등록 화면: GET  /customer/notices/add
 * - 등록 처리: POST /customer/notices/add
 * - 수정 화면: GET  /customer/notices/{id}/edit
 * - 수정 처리: POST /customer/notices/{id}/edit
 */
@Controller
@RequestMapping("/customer/notices")
@RequiredArgsConstructor
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
    public String addForm() {
        return "customer/noticeAddForm";
    }

    /**
     * 등록 처리
     */
    @PostMapping("/add")
    public String add(@ModelAttribute("noticeAddDto") NoticeAddDto noticeAddDto,
                      RedirectAttributes redirectAttributes) {
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
                       @ModelAttribute("noticeEditDto") NoticeEditDto noticeEditDto) {
        noticeService.update(noticeNo, noticeEditDto);
        return "redirect:/customer/notices/{noticeNo}";
    }

    /**
     * 삭제 처리
     */
    /*
    @PostMapping("/remove")
    @ResponseBody
    public String remove(@RequestBody Integer[] noticeNoList) {
        noticeService.deleteByIds(noticeNoList);
        return "customer/noticeDetail";
    }
    */
}
