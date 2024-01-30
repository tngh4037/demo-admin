package com.example.demo.global.converter;

import com.example.demo.global.define.EnumMapperType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO :: 로직 재점검
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<? extends EnumMapperType>> {

    @Override
    public <T extends Enum<? extends EnumMapperType>> Converter<String, T> getConverter(Class<T> targetType) {
        return new CodeToEnumConverter<>(targetType);
    }

    private static final class CodeToEnumConverter<T extends Enum<? extends EnumMapperType>> implements Converter<String, T> {

        private final Map<String, T> map;

        public CodeToEnumConverter(Class<T> targetEnum) {
            map = Arrays.stream(targetEnum.getEnumConstants())
                    .collect(Collectors.toMap(enumConstant -> ((EnumMapperType) enumConstant).getCode(), Function.identity()));
        }

        @Override
        public T convert(String source) {
            // 해당 값 존재 여부 확인
            if (!StringUtils.hasText(source)) {
                return null;
            }

            T value = map.get(source);
            if (value == null) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
            return value;
        }
    }
}