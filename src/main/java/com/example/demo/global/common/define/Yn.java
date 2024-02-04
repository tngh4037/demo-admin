package com.example.demo.global.common.define;

import com.example.demo.global.config.define.EnumMapperType;
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