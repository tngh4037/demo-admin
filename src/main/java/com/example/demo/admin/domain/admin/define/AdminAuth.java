package com.example.demo.admin.domain.admin.define;

import com.example.demo.admin.global.config.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminAuth implements EnumMapperType {
    MASTER("01", "마스터 관리자"),
    DEVELOPER("02", "개발 담당자"),
    MANAGER("03", "서비스 운영자"),
    FINANCIAL("04", "회계 담당자"),
    SALES("05", "영업 담당자"),
    CUSTOMER("06", "고객 담당자");

    private final String code;
    private final String title;
}