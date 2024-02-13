package com.example.demo.admin.domain.admin.controller;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.dto.AdminAddDto;
import com.example.demo.admin.domain.admin.dto.AdminEditDto;
import com.example.demo.admin.domain.admin.dto.AdminResponseDto;
import com.example.demo.admin.domain.admin.dto.AdminSearchDto;
import com.example.demo.admin.domain.admin.service.AdminService;
import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.common.ValidationSequence;
import com.example.demo.admin.global.common.constant.PageConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.global.config.argumentresolver.LoginAdmin;
import com.example.demo.admin.global.util.ErrorUtil;

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
 * - 목록 조회: GET     /admins
 * - 상세 조회: GET     /admins/{id}
 * - 상세 조회: POST    /admins/{id}
 * - 등록 화면: GET     /admins/add
 * - 등록 처리: POST    /admins/add
 * - 수정 화면: GET     /admins/{id}/edit
 * - 수정 처리: POST    /admins/{id}/edit
 * - 잠금 해제: POST    /admins/{id}/edit/unlock
 * - 실패 초기화: POST  /admins/{id}/edit/failcnt
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
    public JsonResult<AdminResponseDto> detailData(@PathVariable("adminNo") Integer adminNo) {
        return JsonResult.ok(new AdminResponseDto(adminService.findByAdminNo(adminNo)));
    }

    @GetMapping("/add")
    public String addForm() {
        return ViewConstant.ADMIN_ADMIN_ADD_FORM;
    }

    @PostMapping("/add")
    @ResponseBody
    public JsonResult<?> add(@RequestBody @Validated(ValidationSequence.class) AdminAddDto adminAddDto,
                             @LoginAdmin(field = "adminNo") Integer worker) {
        Admin admin = adminService.save(adminAddDto);
        log.info("admin account add [worker: {}] [target: {}]", worker, admin.getAdminNo());
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
                              @RequestBody @Validated(ValidationSequence.class) AdminEditDto adminEditDto,
                              @LoginAdmin(field = "adminNo") Integer worker) {
        adminService.update(adminNo, adminEditDto);
        log.info("admin account edit [worker: {}] [target: {}]", worker, adminNo);
        return JsonResult.ok();
    }

    @PostMapping("/{adminNo}/edit/unlock")
    @ResponseBody
    public JsonResult<?> unlock(@PathVariable("adminNo") Integer adminNo,
                                @LoginAdmin(field = "adminNo") Integer worker) {
        adminService.updateLoginDt(adminNo);
        log.info("admin account unlock [worker: {}] [target: {}]", worker, adminNo);
        return JsonResult.ok();
    }

    @PostMapping("/{adminNo}/edit/failcnt")
    @ResponseBody
    public JsonResult<?> failCnt(@PathVariable("adminNo") Integer adminNo,
                                 @LoginAdmin(field = "adminNo") Integer worker) {
        adminService.initFailCnt(adminNo);
        log.info("admin account failcnt [worker: {}] [target: {}]", worker, adminNo);
        return JsonResult.ok();
    }
}