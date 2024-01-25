package com.example.demo.global.config;

import com.example.demo.global.converter.StringToEnumConverterFactory;
import com.example.demo.global.define.EnumMapperType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        ConverterFactory<String, Enum<? extends EnumMapperType>> converterFactory = new StringToEnumConverterFactory();
        registry.addConverterFactory(converterFactory);
    }
}