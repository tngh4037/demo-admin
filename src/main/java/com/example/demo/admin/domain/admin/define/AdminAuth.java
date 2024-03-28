package com.example.demo.admin.domain.admin.define;

import com.example.demo.admin.global.config.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminAuth implements EnumMapperType {
    MASTER("ROLE_MASTER", "마스터 관리자"),
    DEVELOPER("ROLE_DEVELOPER", "개발 담당자"),
    MANAGER("ROLE_MANAGER", "운영 담당자"),
    FINANCIAL("ROLE_FINANCIAL", "회계 담당자"),
    SALES("ROLE_SALES", "영업 담당자"),
    CUSTOMER("ROLE_CUSTOMER", "고객 담당자");

    private final String code;
    private final String title;
}