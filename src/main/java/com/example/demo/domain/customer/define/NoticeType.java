package com.example.demo.domain.customer.define;

import com.example.demo.global.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType implements EnumMapperType {
    EVENT("EV", "이벤트"),
    NOTICE("NO", "공지"),
    GOODS("GO", "상품"),
    USER("US", "회원"),
    ETC("ET", "기타");

    private final String code;
    private final String title;
}