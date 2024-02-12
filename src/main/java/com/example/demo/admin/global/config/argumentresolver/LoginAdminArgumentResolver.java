package com.example.demo.admin.global.config.argumentresolver;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.global.common.constant.SessionConstant;
import com.example.demo.admin.global.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Slf4j
public class LoginAdminArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginAdmin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConstant.LOGIN_ADMIN) == null) {
            return null;
        }

        Admin admin = (Admin) session.getAttribute(SessionConstant.LOGIN_ADMIN);
        String field = Objects.requireNonNull(parameter.getParameterAnnotation(LoginAdmin.class)).field();

        if (CommonUtil.isEmpty(field)) {
            return admin;
        } else {
            return Objects.requireNonNull(BeanUtils.getPropertyDescriptor(admin.getClass(), field)).getReadMethod().invoke(admin);
        }
    }
}
