package com.example.demo.admin.domain.admin.domain;

import com.example.demo.admin.domain.admin.define.AdminAuth;
import com.example.demo.admin.domain.admin.define.AdminStatus;
import com.example.demo.admin.global.common.constant.SecurityConstant;
import com.example.demo.admin.global.common.define.PrivacyType;
import com.example.demo.admin.global.util.RegExpUtil;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {
    private Integer adminNo;
    private String adminId;
    private String adminPwd;
    private AdminAuth adminAuth;
    private AdminStatus adminStatus;
    private Integer failCnt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDt;
    private LocalDateTime pwdDt;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Admin(Integer adminNo, String adminId, String adminPwd, AdminAuth adminAuth, AdminStatus adminStatus,
                 Integer failCnt, LocalDateTime loginDt, LocalDateTime pwdDt,
                 LocalDateTime regDt, LocalDateTime modDt) {
        this.adminNo = adminNo;
        this.adminId = adminId;
        this.adminPwd = adminPwd;
        this.adminAuth = adminAuth;
        this.adminStatus = adminStatus;
        this.failCnt = failCnt;
        this.loginDt = loginDt;
        this.pwdDt = pwdDt;
        this.regDt = regDt;
        this.modDt = modDt;
    }

    /**
     * 개인정보 masking 처리
     */
    public void setMasking() {
        this.adminId = RegExpUtil.getMaskingPrivacy(PrivacyType.ID, this.adminId);
    }

    /**
     * 잠금 대상자 인지 체크
     * - 마지막 로그인 날짜로 부터 90일간 로그인 이력이 없는 경우, 서비스 이용 불가한 잠금 대상이 된다.
     * - 만약 계정 생성된 후 한 번도 로그인 하지 않았다면, 기준 날짜는 계정 생성일로 한다.
     * @return boolean
     */
    public boolean isLockTarget() {
        LocalDate targetDt;
        if (loginDt == null) {
            targetDt = regDt.toLocalDate();
        } else {
            targetDt = loginDt.toLocalDate();
        }

        return targetDt.plusDays(SecurityConstant.LOCK_TARGET_DAYS).isBefore(LocalDate.now());
    }

    /**
     * 비밀번호 실패 건수 초기화 대상자 인지 체크
     * - 대상자: SecurityConstant.MAX_PWD_FAIL_CNT 회 이상 비밀번호 불일치로 로그인에 실패한 경우
     */
    public boolean isFailCntInitTarget() {
        return this.failCnt >= SecurityConstant.MAX_PWD_FAIL_CNT;
    }
}