package com.example.demo.admin.domain.login.controller;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.login.dto.AdminLoginDto;
import com.example.demo.admin.domain.login.exception.LoginFailException;
import com.example.demo.admin.domain.login.service.LoginService;
import com.example.demo.admin.global.common.constant.SessionConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("adminLoginDto") AdminLoginDto adminLoginDto) {
        return ViewConstant.LOGIN_LOGIN_FORM;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("adminLoginDto") AdminLoginDto adminLoginDto,
                        BindingResult bindingResult,
                        @RequestParam(value = "redirectURL", defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ViewConstant.LOGIN_LOGIN_FORM;
        }

        Admin loginAdmin;
        try {
            loginAdmin = loginService.login(adminLoginDto.getAdminId(), adminLoginDto.getAdminPwd());
        } catch (LoginFailException ex) {
            bindingResult.addError(new ObjectError("adminLoginDto", ex.getMessage()));
            log.info("errors = {}", bindingResult);
            return ViewConstant.LOGIN_LOGIN_FORM;
        }

        // 로그인 성공 처리 ( 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성 )
        HttpSession session = request.getSession();

        // 세션에 로그인 정보 보관
        session.setAttribute(SessionConstant.LOGIN_ADMIN, loginAdmin);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}