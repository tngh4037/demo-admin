package com.example.demo.domain.admin.controller;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminAddDto;
import com.example.demo.domain.admin.dto.AdminEditDto;
import com.example.demo.domain.admin.dto.AdminSearchDto;
import com.example.demo.domain.admin.service.AdminService;
import com.example.demo.global.common.JsonResult;
import com.example.demo.global.common.PaginationDto;
import com.example.demo.global.common.constant.PageConstant;
import com.example.demo.global.common.constant.ViewConstant;
import com.example.demo.global.util.ErrorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [어드민 관리]
 * 1) URI 구조
 * - 목록 조회: GET   /admins
 * - 상세 조회: GET   /admins/{id}
 * - 상세 조회: POST  /admins/{id}
 * - 등록 화면: GET   /admins/add
 * - 등록 처리: POST  /admins/add
 * - 수정 화면: GET   /admins/{id}/edit
 * - 수정 처리: POST  /admins/{id}/edit
 *
 * 2) 참고
 * - (BindingResult / BeanValidation)을 통한 client 요청 값 검증 및 redirect 처리
 * - 웹 클라이언트(Ajax)를 통한 (저장 / 수정 / 삭제)
 *   ㄴ 요청 / 응답 형태 : json
 */
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
    public String detailPage(@PathVariable("adminNo") Integer adminNo, Model model) {
        Admin admin = adminService.findByAdminNo(adminNo);
        model.addAttribute("admin", admin);
        return ViewConstant.ADMIN_ADMIN_DETAIL;
    }

    @PostMapping("/{adminNo}")
    @ResponseBody
    public JsonResult<Admin> detailData(@PathVariable("adminNo") Integer adminNo) {
        return JsonResult.ok(adminService.findByAdminNo(adminNo));
    }

    @GetMapping("/add")
    public String addForm() {
        return ViewConstant.ADMIN_ADMIN_ADD_FORM;
    }

    @PostMapping("/add")
    @ResponseBody
    public JsonResult<?> add(@RequestBody AdminAddDto adminAddDto) {
        adminService.save(adminAddDto);
        return JsonResult.ok();
    }

    @GetMapping("/{adminNo}/edit")
    public String editForm(@PathVariable("adminNo") Integer adminNo, Model model) {
        Admin admin = adminService.findByAdminNo(adminNo);
        model.addAttribute("admin", admin);
        return ViewConstant.ADMIN_ADMIN_EDIT_FORM;
    }

    @PostMapping("/{adminNo}/edit")
    @ResponseBody
    public JsonResult<?> edit(@PathVariable("adminNo") Integer adminNo,
                              @RequestBody AdminEditDto adminEditDto) {
        adminService.update(adminNo, adminEditDto);
        return JsonResult.ok();
    }
}