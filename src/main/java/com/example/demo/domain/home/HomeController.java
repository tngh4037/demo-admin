package com.example.demo.domain.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.global.constant.ViewConstant;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return ViewConstant.MAIN;
    }
}