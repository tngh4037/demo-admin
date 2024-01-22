package com.example.demo.domain.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.global.constant.ViewConstant;

/**
 * 화면 구성을 위한 샘플 페이지
 */
@Controller
public class SampleController {

    /**
     * 리스트 페이지
     */
    @GetMapping("/sample/list")
    public String getSampleList() {
        return ViewConstant.SAMPLE_LIST;
    }

    /**
     * 상세보기 페이지
     */
    @GetMapping("/sample/detail")
    public String getSampleDetail() {
        return ViewConstant.SAMPLE_DETAIL;
    }
}
