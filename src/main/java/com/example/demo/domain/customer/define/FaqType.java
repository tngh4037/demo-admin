package com.example.demo.domain.customer.define;

import com.example.demo.global.config.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FaqType implements EnumMapperType {
    EVENT("EV", "이벤트"),
    BUY("GO", "주문/결제"),
    USER("US", "회원"),
    CANCEL("CA", "취소/반품"),
    DELIVER("DE", "배송");

    private final String code;
    private final String title;
}