package com.example.demo.admin.global.common.define;

import com.example.demo.admin.global.config.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotifyType implements EnumMapperType {
    SMS("SMS", "문자"),
    EMAIL("EMAIL", "이메일"),
    SLACK("SLACK", "슬랙");

    private final String code;
    private final String title;
}