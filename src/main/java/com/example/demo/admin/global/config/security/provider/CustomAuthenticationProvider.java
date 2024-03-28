package com.example.demo.admin.global.config.security.provider;

import com.example.demo.admin.domain.admin.define.AdminStatus;
import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.login.service.LoginService;
import com.example.demo.admin.global.common.constant.SecurityConstant;
import com.example.demo.admin.global.config.security.service.AccountContext;
import com.example.demo.admin.global.util.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) customUserDetailsService.loadUserByUsername(username);
        Admin admin = accountContext.getAccount();

        if (!passwordEncoder.matches(password, admin.getAdminPwd())) {
            loginService.loginFail(admin.getAdminNo());
            throw new BadCredentialsException(MessageHelper.getMessage("login.invalid.adminPwd.not.match"));
        }

        if (admin.getAdminStatus().isStop()) {
            throw new DisabledException(MessageHelper.getMessage("login.invalid.adminStatus", new Object[]{AdminStatus.STOP.getTitle()}));
        }

        if (admin.isLockTarget()) {
            throw new LockedException(MessageHelper.getMessage("login.invalid.lock.target", new Object[]{SecurityConstant.LOCK_TARGET_DAYS}));
        }

        if (admin.isFailCntInitTarget()) {
            throw new CredentialsExpiredException(MessageHelper.getMessage("login.invalid.fail.cnt.target", new Object[]{SecurityConstant.MAX_PWD_FAIL_CNT}));
        }

        loginService.loginSuccess(admin.getAdminNo());

        return new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
