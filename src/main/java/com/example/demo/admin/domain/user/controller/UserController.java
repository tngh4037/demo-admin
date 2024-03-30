package com.example.demo.admin.domain.user.controller;

import com.example.demo.admin.global.common.constant.ViewConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String list() {
        return ViewConstant.USER_LIST;
    }
}
