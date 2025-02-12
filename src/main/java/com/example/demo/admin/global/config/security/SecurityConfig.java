package com.example.demo.admin.global.config.security;

import com.example.demo.admin.global.config.security.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private AuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Authentication
        http
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("adminId")
                        .passwordParameter("adminPwd")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler))
                .authenticationProvider(customAuthenticationProvider())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"));

        // Authorization
        http
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(getResourceOpenPath()).permitAll()
                        .requestMatchers("/login*").permitAll()
                        .requestMatchers("/users/**").hasAnyRole("MASTER", "DEVELOPER", "MANAGER", "CUSTOMER")
                        .requestMatchers("/goods/**").hasAnyRole("MASTER", "DEVELOPER", "MANAGER")
                        .requestMatchers("/sales/**").hasAnyRole("MASTER", "DEVELOPER", "MANAGER", "FINANCIAL", "SALES")
                        .requestMatchers("/customer/**").hasAnyRole("MASTER", "DEVELOPER", "MANAGER", "CUSTOMER")
                        .requestMatchers("/admins/**").hasRole("MASTER")
                        .anyRequest().authenticated());

        // ExceptionHandling
        http
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    private String[] getResourceOpenPath() {
        return new String[]{"/coreui/**", "/css/**", "/images/**", "/js/common/**", "/*-icon-*", "/favicon.*"};
    }

}

// 참고)
// SessionManagement
// https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
/*
http
		.sessionManagement((sessionManagement) -> sessionManagement
				.sessionFixation().changeSessionId()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true)
				.expiredUrl("/expired"));
*/