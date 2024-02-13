package com.example.demo.admin.domain.admin.dto;

import com.example.demo.admin.domain.admin.define.AdminAuth;
import com.example.demo.admin.domain.admin.define.AdminStatus;
import com.example.demo.admin.domain.admin.domain.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminResponseDto {
    private final String adminId;
    private final AdminAuth adminAuth;
    private final AdminStatus adminStatus;
    private final LocalDateTime regDt;
    private final LocalDateTime modDt;

    public AdminResponseDto(Admin admin) {
        this.adminId = admin.getAdminId();
        this.adminAuth = admin.getAdminAuth();
        this.adminStatus = admin.getAdminStatus();
        this.regDt = admin.getRegDt();
        this.modDt = admin.getModDt();
    }
}
