package com.example.demo.domain.common.define;

import com.example.demo.global.define.EnumMapperType;
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