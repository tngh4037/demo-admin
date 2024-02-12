package com.example.demo.admin.global.config.interceptor;

import com.example.demo.admin.global.common.constant.SessionConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConstant.LOGIN_ADMIN) == null) {
            log.info("미인증 사용자 요청 requestURI = [{}]", requestURI);
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}