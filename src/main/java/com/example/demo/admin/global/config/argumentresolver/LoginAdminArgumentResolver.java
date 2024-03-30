package com.example.demo.admin.global.config.argumentresolver;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.global.util.CommonUtil;
import com.example.demo.admin.global.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.InvocationTargetException;
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

        try {
            if (!SecurityUtil.isAuthenticated()) {
                return null;
            }

            Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String field = Objects.requireNonNull(parameter.getParameterAnnotation(LoginAdmin.class)).field();

            if (CommonUtil.isEmpty(field)) {
                return admin;
            } else {
                return Objects.requireNonNull(BeanUtils.getPropertyDescriptor(admin.getClass(), field)).getReadMethod().invoke(admin);
            }
        } catch (BeansException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
