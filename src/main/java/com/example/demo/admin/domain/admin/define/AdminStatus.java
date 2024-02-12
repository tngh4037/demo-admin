package com.example.demo.admin.domain.admin.define;

import com.example.demo.admin.global.config.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminStatus implements EnumMapperType {
    ACTIVE("01", "정상"),
    STOP("02", "중지");

    private final String code;
    private final String title;

    public boolean isStop() {
        return this == STOP;
    }
}