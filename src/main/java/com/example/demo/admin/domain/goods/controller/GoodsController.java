package com.example.demo.admin.domain.goods.controller;

import com.example.demo.admin.global.common.constant.ViewConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @GetMapping
    public String list() {
        return ViewConstant.GOODS_LIST;
    }
}
