package com.example.demo.admin.domain.admin.controller.api;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.dto.AdminAddDto;
import com.example.demo.admin.domain.admin.dto.AdminEditDto;
import com.example.demo.admin.domain.admin.dto.AdminResponseDto;
import com.example.demo.admin.domain.admin.service.AdminService;
import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.ValidationSequence;
import com.example.demo.admin.global.config.argumentresolver.LoginAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminApi {

    private final AdminService adminService;

    @PostMapping("/{adminNo}")
    public JsonResult<AdminResponseDto> detail(@PathVariable("adminNo") Integer adminNo) {
        return JsonResult.ok(new AdminResponseDto(adminService.findByAdminNo(adminNo)));
    }

    @PostMapping("/add")
    public JsonResult<?> add(@RequestBody @Validated(ValidationSequence.class) AdminAddDto adminAddDto,
                             @LoginAdmin(field = "adminNo") Integer worker) {
        Admin admin = adminService.save(adminAddDto);
        log.info("admin account add [worker: {}] [target: {}]", worker, admin.getAdminNo());
        return JsonResult.ok();
    }

    @PostMapping("/{adminNo}/edit")
    public JsonResult<?> edit(@PathVariable("adminNo") Integer adminNo,
                              @RequestBody @Validated(ValidationSequence.class) AdminEditDto adminEditDto,
                              @LoginAdmin(field = "adminNo") Integer worker) {
        adminService.update(adminNo, adminEditDto);
        log.info("admin account edit [worker: {}] [target: {}]", worker, adminNo);
        return JsonResult.ok();
    }

    @PostMapping("/{adminNo}/edit/unlock")
    public JsonResult<?> unlock(@PathVariable("adminNo") Integer adminNo,
                                @LoginAdmin(field = "adminNo") Integer worker) {
        adminService.updateLoginDt(adminNo);
        log.info("admin account unlock [worker: {}] [target: {}]", worker, adminNo);
        return JsonResult.ok();
    }

    @PostMapping("/{adminNo}/edit/failcnt")
    public JsonResult<?> failCnt(@PathVariable("adminNo") Integer adminNo,
                                 @LoginAdmin(field = "adminNo") Integer worker) {
        adminService.initFailCnt(adminNo);
        log.info("admin account failcnt [worker: {}] [target: {}]", worker, adminNo);
        return JsonResult.ok();
    }
}
