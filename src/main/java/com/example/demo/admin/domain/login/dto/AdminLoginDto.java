package com.example.demo.admin.domain.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminLoginDto {
    @NotEmpty(message = "아이디를 입력해 주세요.")
    private String adminId;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String adminPwd;
}