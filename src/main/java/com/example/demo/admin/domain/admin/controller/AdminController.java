package com.example.demo.admin.domain.admin.controller;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.dto.AdminSearchDto;
import com.example.demo.admin.domain.admin.service.AdminService;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.common.constant.PageConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.global.util.ErrorUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String list(@Validated @ModelAttribute("searchDto") AdminSearchDto adminSearchDto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("msg", ErrorUtil.getBindingMessage(bindingResult));
            model.addAttribute("url", "/admins");
            return ViewConstant.COMMON_REDIRECT;
        }

        PaginationDto paginationDto = new PaginationDto(adminSearchDto.getPageNo(),
                PageConstant.COMMON_RECORD_COUNT, PageConstant.COMMON_PAGE_SIZE);
        List<Admin> adminList = adminService.findAll(adminSearchDto, paginationDto);

        model.addAttribute("totalCount", paginationDto.getTotalRecordCount());
        model.addAttribute("paginationDto", paginationDto);
        model.addAttribute("adminList", adminList);

        return ViewConstant.ADMIN_ADMIN_LIST;
    }

    @GetMapping("/{adminNo}")
    public String detailForm(@PathVariable("adminNo") Integer adminNo, Model model) {
        model.addAttribute("admin", adminService.findByAdminNo(adminNo));
        return ViewConstant.ADMIN_ADMIN_DETAIL;
    }

    @GetMapping("/add")
    public String addForm() {
        return ViewConstant.ADMIN_ADMIN_ADD_FORM;
    }

    @GetMapping("/{adminNo}/edit")
    public String editForm(@PathVariable("adminNo") Integer adminNo, Model model) {
        model.addAttribute("admin", adminService.findByAdminNo(adminNo));
        return ViewConstant.ADMIN_ADMIN_EDIT_FORM;
    }
}