package com.example.demo.admin.domain.login.service;

import com.example.demo.admin.domain.admin.define.AdminStatus;
import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.repository.AdminRepository;
import com.example.demo.admin.domain.login.exception.LoginFailException;
import com.example.demo.admin.global.common.constant.SecurityConstant;
import com.example.demo.admin.global.util.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO :: 패스워드 추가 정책 정의하기(암호화 등)
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminRepository adminRepository;

    public Admin login(String adminId, String adminPwd) {
        Admin admin = validateAndGet(adminId, adminPwd);
        loginSuccess(admin.getAdminNo());
        return admin;
    }

    private Admin validateAndGet(String adminId, String adminPwd) {
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(() -> new LoginFailException(MessageHelper.getMessage("login.invalid.member.not.match")));

        if (!admin.getAdminPwd().equals(adminPwd)) {
            adminRepository.updateFailCnt(admin.getAdminNo(), 1);
            throw new LoginFailException(MessageHelper.getMessage("login.invalid.adminPwd.not.match"));
        }

        if (admin.getAdminStatus().isStop()) {
            throw new LoginFailException(MessageHelper.getMessage("login.invalid.adminStatus", new Object[]{AdminStatus.STOP.getTitle()}));
        }

        if (admin.isFailCntInitTarget()) {
            throw new LoginFailException(MessageHelper.getMessage("login.invalid.fail.cnt.target", new Object[]{SecurityConstant.MAX_PWD_FAIL_CNT}));
        }

        if (admin.isLockTarget()) {
            throw new LoginFailException(MessageHelper.getMessage("login.invalid.lock.target", new Object[]{SecurityConstant.LOCK_TARGET_DAYS}));
        }

        return admin;
    }

    private void loginSuccess(Integer adminNo) {
        adminRepository.updateByLogin(adminNo);
    }
}