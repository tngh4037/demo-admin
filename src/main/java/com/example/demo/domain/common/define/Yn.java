package com.example.demo.domain.common.define;

import com.example.demo.global.define.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Yn implements EnumMapperType {
    YES("Y", "YES"),
    NO("N", "NO");
    
    private final String code;
    private final String title;
}