package com.example.demo.domain.admin.service;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminAddDto;
import com.example.demo.domain.admin.dto.AdminEditDto;
import com.example.demo.domain.admin.dto.AdminSearchDto;
import com.example.demo.domain.admin.exception.AdminAlreadyExistException;
import com.example.demo.domain.admin.exception.AdminNotFoundException;
import com.example.demo.domain.admin.exception.PasswordPolicyException;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.global.common.PaginationDto;
import com.example.demo.global.util.RegExpPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> findAll(AdminSearchDto adminSearchDto, PaginationDto paginationDto) {
        List<Admin> adminList = new ArrayList<>();
        int count = adminRepository.count(adminSearchDto);
        if (count > 0) {
            paginationDto.setTotalRecordCount(count);
            adminList = adminRepository.findAll(adminSearchDto, paginationDto);
            adminList.forEach(Admin::setMasking);
        }

        return adminList;
    }

    public Admin findByAdminNo(Integer adminNo) {
        return adminRepository.findByAdminNo(adminNo).orElseThrow(AdminNotFoundException::new);
    }

    public Admin save(AdminAddDto adminAddDto) {
        checkPwdInput(adminAddDto.getAdminPwd(), adminAddDto.getAdminRePwd());
        checkAlreadyExistId(adminAddDto.getAdminId());
        return adminRepository.save(adminAddDto.toEntity());
    }

    public void update(Integer adminNo, AdminEditDto adminEditDto) {
        Admin admin = findByAdminNo(adminNo);
        if (adminEditDto.isModifyPwd()) {
            checkPwdInput(adminEditDto.getAdminPwd(), adminEditDto.getAdminRePwd());
            checkPwdPattern(adminEditDto.getAdminPwd());
            checkBeforePwd(admin.getAdminPwd(), adminEditDto.getAdminPwd());
        }

        adminRepository.update(adminNo, adminEditDto.toEntity());
    }

    public void updateLoginDt(Integer adminNo) {
        Admin admin = findByAdminNo(adminNo);
        if (!admin.isLockTarget()) {
            throw new AdminNotFoundException("잠금 해제 대상 계정이 아닙니다.");
        }
        adminRepository.updateLoginDt(admin.getAdminNo());
    }

    public void initFailCnt(Integer adminNo) {
        Admin admin = findByAdminNo(adminNo);
        if (!admin.isFailCntInitTarget()) {
            throw new AdminNotFoundException("비밀번호 실패 건수 초기화 대상 계정이 아닙니다.");
        }
        adminRepository.updateFailCnt(admin.getAdminNo(), 0);
    }

    private void checkPwdInput(String pwd, String rePwd) {
        if (!pwd.equals(rePwd)) {
            throw new PasswordPolicyException("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
        }
    }

    private void checkPwdPattern(String pwd) {
        if (!Pattern.matches(RegExpPattern.PWD_PATTERN, pwd)) {
            throw new PasswordPolicyException("비밀번호는 영문 숫자 특수기호 조합 8~16자리로 입력 가능합니다.");
        }
    }

    private void checkBeforePwd(String beforePwd, String pwd) {
        if (beforePwd.equals(pwd)) {
            throw new PasswordPolicyException("기존 비밀번호와 동일한 비밀번호로는 변경이 불가합니다.");
        }
    }

    private void checkAlreadyExistId(String adminId) {
        adminRepository.findByAdminId(adminId).ifPresent(m -> {
            throw new AdminAlreadyExistException();
        });
    }
}
