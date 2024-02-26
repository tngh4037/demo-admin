package com.example.demo.admin.global.common.define;

import com.example.demo.admin.global.config.define.EnumMapperType;
import com.example.demo.admin.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Yn implements EnumMapperType {
    YES("Y", "YES"),
    NO("N", "NO");
    
    private final String code;
    private final String title;

    public static Yn findByCode(String code) {
        return Arrays.stream(Yn.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElseThrow(InvalidValueException::new);
    }
}