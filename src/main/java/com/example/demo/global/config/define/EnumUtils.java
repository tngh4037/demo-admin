package com.example.demo.global.config.define;

import java.util.EnumSet;

public class EnumUtils {

    public static <T extends Enum<T> & EnumMapperType> T getCodeEnum(Class<T> enumClass, String code) {
        return EnumSet.allOf(enumClass).stream()
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
