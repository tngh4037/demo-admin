package com.example.demo.admin.domain.login.controller;

import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        if (SecurityUtil.isAuthenticated()) {
            model.addAttribute("msg", "이미 로그인 중입니다.");
            model.addAttribute("url", "/");
            return ViewConstant.COMMON_REDIRECT;
        }

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return ViewConstant.LOGIN_LOGIN_FORM;
    }
}