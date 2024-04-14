package com.example.demo.admin.global.config.security.handler;

import com.example.demo.admin.global.error.advice.ErrorResponse;
import com.example.demo.admin.global.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (WebUtil.isAjaxRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            try {
                String json = new ObjectMapper().writeValueAsString(
                        ErrorResponse.of(HttpServletResponse.SC_UNAUTHORIZED, "로그인 후 이용 가능합니다."));
                response.getWriter().write(json);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            response.sendRedirect("/login");
        }
    }
}
