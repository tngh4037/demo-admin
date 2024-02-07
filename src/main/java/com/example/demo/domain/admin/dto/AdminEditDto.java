package com.example.demo.domain.admin.dto;

import com.example.demo.domain.admin.define.AdminAuth;
import com.example.demo.domain.admin.define.AdminStatus;
import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.global.common.ValidationGroups;
import com.example.demo.global.util.CommonUtil;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminEditDto {

    private String adminPwd;
    private String adminRePwd;

    @NotNull(message = "관리자 권한을 지정해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private AdminAuth adminAuth;

    @NotNull(message = "관리자 상태를 지정해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private AdminStatus adminStatus;

    public Admin toEntity() {
        return Admin.of()
                .adminPwd(this.adminPwd)
                .adminAuth(this.adminAuth)
                .adminStatus(this.adminStatus)
                .build();
    }

    public boolean isModifyPwd() {
        return !CommonUtil.isEmpty(this.adminPwd);
    }
}
