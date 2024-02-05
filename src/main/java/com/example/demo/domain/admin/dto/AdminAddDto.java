package com.example.demo.domain.admin.dto;

import com.example.demo.domain.admin.define.AdminAuth;
import com.example.demo.domain.admin.define.AdminStatus;
import com.example.demo.domain.admin.domain.Admin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminAddDto {
    private String adminId;
    private String adminPwd;
    private AdminAuth adminAuth;
    private AdminStatus adminStatus;

    public Admin toEntity() {
        return Admin.of()
                .adminId(this.adminId)
                .adminPwd(this.adminPwd)
                .adminAuth(this.adminAuth)
                .adminStatus(this.adminStatus)
                .build();
    }
}
