package com.example.demo.admin.global.config.security.handler;

import com.example.demo.admin.global.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (isAjaxRequest(request)) {
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

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader(AJAX_HEADER_NAME);
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        return AJAX_HEADER_VALUE.equals(header) || MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader);
    }
}
