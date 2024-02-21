package com.example.demo.admin.domain.admin.service;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.dto.AdminAddDto;
import com.example.demo.admin.domain.admin.dto.AdminEditDto;
import com.example.demo.admin.domain.admin.dto.AdminSearchDto;
import com.example.demo.admin.domain.admin.exception.AdminDuplicateException;
import com.example.demo.admin.domain.admin.exception.AdminNotFoundException;
import com.example.demo.admin.domain.admin.exception.PasswordPolicyException;
import com.example.demo.admin.domain.admin.repository.AdminRepository;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.util.MessageHelper;
import com.example.demo.admin.global.util.RegExpPattern;
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
            throw new AdminNotFoundException(MessageHelper.getMessage("admin.invalid.lock.target"));
        }
        adminRepository.updateLoginDt(admin.getAdminNo());
    }

    public void initFailCnt(Integer adminNo) {
        Admin admin = findByAdminNo(adminNo);
        if (!admin.isFailCntInitTarget()) {
            throw new AdminNotFoundException(MessageHelper.getMessage("admin.invalid.fail.cnt.target"));
        }
        adminRepository.updateFailCnt(admin.getAdminNo(), 0);
    }

    private void checkPwdInput(String pwd, String rePwd) {
        if (!pwd.equals(rePwd)) {
            throw new PasswordPolicyException(MessageHelper.getMessage("admin.invalid.adminPwd.not.match"));
        }
    }

    private void checkPwdPattern(String pwd) {
        if (!Pattern.matches(RegExpPattern.PWD_PATTERN, pwd)) {
            throw new PasswordPolicyException(MessageHelper.getMessage("admin.invalid.adminPwd.pattern"));
        }
    }

    private void checkBeforePwd(String beforePwd, String pwd) {
        if (beforePwd.equals(pwd)) {
            throw new PasswordPolicyException(MessageHelper.getMessage("admin.invalid.adminPwd.same.before"));
        }
    }

    private void checkAlreadyExistId(String adminId) {
        adminRepository.findByAdminId(adminId).ifPresent(m -> {
            throw new AdminDuplicateException();
        });
    }
}
