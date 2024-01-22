package com.example.demo.domain.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 화면 구성을 위한 샘플 페이지
 */
@Controller
public class SampleController {

    /**
     * list page
     */
    @GetMapping("/sample/list")
    public String getSampleList() {
        return "sample/sampleList";
    }

    /**
     * detail page
     */
    @GetMapping("/sample/detail")
    public String getSampleDetail() {
        return "sample/sampleDetail";
    }
}
