package com.example.demo.admin.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    /**
     * 화면 구성을 위한 샘플 페이지
     * - pageMode(search): 조회 리스트 페이지
     * - pageMode(detail):조회 상세 / 등록 / 수정 페이지
     */
    @GetMapping("/")
    public String getHome(@RequestParam(value = "pageMode", required = false, defaultValue = "search") String pageMode) {
        if (pageMode.equals("detail")) {
            return "sample/sampleDetail";
        }
        return "/sample/sampleList";
    }
}