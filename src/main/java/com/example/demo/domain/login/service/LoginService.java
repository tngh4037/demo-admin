package com.example.demo.domain.login.service;

import com.example.demo.domain.admin.define.AdminStatus;
import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.domain.login.exception.LoginFailException;
import com.example.demo.global.common.constant.SecurityConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO :: 패스워드 추가 정책 정의하기(암호화 등) + 메시지 공통 관리
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
                .orElseThrow(() -> new LoginFailException("존재하지 않는 계정입니다. 입력하신 내용을 다시 확인해주세요."));

        if (!admin.getAdminPwd().equals(adminPwd)) {
            adminRepository.updateFailCnt(admin.getAdminNo(), 1);
            throw new LoginFailException("비밀번호가 일치하지 않습니다. 입력하신 내용을 다시 확인해주세요.");
        }

        if (admin.getAdminStatus().isStop()) {
            throw new LoginFailException(AdminStatus.STOP.getTitle() + " 처리된 계정입니다. 마스터 관리자에 문의해 주세요.");
        }

        if (admin.isFailCntInitTarget()) {
            throw new LoginFailException("비밀번호 입력을 " + SecurityConstant.MAX_PWD_FAIL_CNT +
                    "회 이상 실패하여 서비스를 이용하실 수 없습니다. 마스터 관리자에 문의해 주세요.");
        }

        if (admin.isLockTarget()) {
            throw new LoginFailException("마지막 로그인 날짜로 부터 " + SecurityConstant.LOCK_TARGET_DAYS +
                    "일이 지나 잠금 처리 되었습니다. 마스터 관리자에 문의해 주세요.");
        }

        return admin;
    }

    private void loginSuccess(Integer adminNo) {
        adminRepository.updateByLogin(adminNo);
    }
}