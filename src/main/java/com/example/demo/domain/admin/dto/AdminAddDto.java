package com.example.demo.domain.admin.dto;

import com.example.demo.domain.admin.define.AdminAuth;
import com.example.demo.domain.admin.define.AdminStatus;
import com.example.demo.domain.admin.domain.Admin;

import com.example.demo.global.common.ValidationGroups;
import com.example.demo.global.util.RegExpPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminAddDto {

    @NotBlank(message = "아이디를 입력해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = RegExpPattern.ID_PATTERN, message = "아이디는 영문 소문자 숫자 4~16자리까지 가능합니다.", groups = ValidationGroups.PatternGroup.class)
    private String adminId;

    @NotBlank(message = "비밀번호를 입력해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = RegExpPattern.PWD_PATTERN, message = "비밀번호는 영문 숫자 특수기호 조합 8~16자리로 입력 가능합니다.", groups = ValidationGroups.PatternGroup.class)
    private String adminPwd;
    private String adminRePwd;

    @NotNull(message = "관리자 권한을 지정해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private AdminAuth adminAuth;

    @NotNull(message = "관리자 상태를 지정해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
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
