package com.example.demo.admin.domain.login.controller;

import com.example.demo.admin.domain.login.dto.AdminLoginDto;
import com.example.demo.admin.global.common.constant.ViewConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("adminLoginDto") AdminLoginDto adminLoginDto) {
        return ViewConstant.LOGIN_LOGIN_FORM;
    }
}