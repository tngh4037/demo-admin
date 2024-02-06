package com.example.demo.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "/sample/sampleList";
    }

    /**
     * (화면 구성을 위한 샘플 페이지) list page
     */
    @GetMapping("/sample/list")
    public String getSampleList() {
        return "sample/sampleList";
    }

    /**
     * (화면 구성을 위한 샘플 페이지) detail page
     */
    @GetMapping("/sample/detail")
    public String getSampleDetail() {
        return "sample/sampleDetail";
    }
}