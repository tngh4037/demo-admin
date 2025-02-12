package com.example.demo.admin.global.config;

import com.example.demo.admin.global.config.argumentresolver.LoginAdminArgumentResolver;
import com.example.demo.admin.global.config.converter.StringToEnumConverterFactory;
import com.example.demo.admin.global.config.define.EnumMapperType;
import com.example.demo.admin.global.config.filter.MDCLogFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        ConverterFactory<String, Enum<? extends EnumMapperType>> converterFactory = new StringToEnumConverterFactory();
        registry.addConverterFactory(converterFactory);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginAdminArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/DemoAdminResource/**")
                .addResourceLocations("file:///C:/DemoAdminResource/");
    }

    /**
     * jsonView 응답시 json 형태로 응답
     */
    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public FilterRegistrationBean<Filter> mdcLogFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new MDCLogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }
}