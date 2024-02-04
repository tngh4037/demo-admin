package com.example.demo.global.config;

import com.example.demo.global.config.converter.StringToEnumConverterFactory;
import com.example.demo.global.config.define.EnumMapperType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        ConverterFactory<String, Enum<? extends EnumMapperType>> converterFactory = new StringToEnumConverterFactory();
        registry.addConverterFactory(converterFactory);
    }

    /**
     * jsonView 응답시 json 형태로 응답
     */
    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }
}