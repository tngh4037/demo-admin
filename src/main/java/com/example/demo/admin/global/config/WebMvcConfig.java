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
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.file.Paths;
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

    // 정적 리소스 요청 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 실제 파일 경로에서 반환
         */
        registry.addResourceHandler("/DemoAdminResource/**") // 클라이언트가 요청하는 URL 패턴
                .addResourceLocations(Paths.get("/DemoAdminResource/").toAbsolutePath().toUri().toString()); // 실제 파일이 저장된 위치를 설정하여 서빙

        /**
         * 정적 리소스 파일 캐싱 방지를 위한 버전 처리
         * - a.css?v=YYYYMMDDHHMMSS
         * - b.js?v=YYYYMMDDHHMMSS
         */
        registry.addResourceHandler("/css/**", "/js/**") // 클라이언트가 /css/**, /js/** 경로로 요청하면 이 요청을 처리할 핸들러를 등록
                .addResourceLocations("classpath:/static/css/", "classpath:/static/js/") // 위 요청이 들어오면, 클래스패스 경로( src/main/resources/static/css/, src/main/resources/static/js/ )에서 해당 정적 파일을 찾음
              //  .setCachePeriod(0) // 브라우저 캐시 사용을 비활성화(0초 동안만 캐싱 -> 즉, 항상 최신 파일을 요청하도록 설정, 개발 중 테스트할 때 유용)
                .resourceChain(true) // 리소스 체인(Resource Chain)을 활성화, VersionResourceResolver 같은 추가적인 리소스 처리 기능을 사용하기 위해 필요
                    .addResolver(new VersionResourceResolver().addVersionStrategy(new CustomVersionStrategy(), "/**")); // 정적 리소스에 버전 정보를 추가하는 VersionResourceResolver를 설정 ( 모든 파일 (/**)에 대해 CustomVersionStrategy()를 사용하여 버전 추가 )
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

    /**
     * 정적 리소스 파일 캐싱 방지를 위한 빈 등록
     * - a.css?v=YYYYMMDDHHMMSS
     * - b.js?v=YYYYMMDDHHMMSS
     */
    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }
}