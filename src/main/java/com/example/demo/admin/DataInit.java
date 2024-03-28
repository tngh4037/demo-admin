package com.example.demo.admin;

import com.example.demo.admin.domain.admin.define.AdminAuth;
import com.example.demo.admin.domain.admin.define.AdminStatus;
import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.dto.AdminAddDto;
import com.example.demo.admin.domain.admin.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class DataInit {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInit(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        AdminAddDto adminAddDto = new AdminAddDto();
        adminAddDto.setAdminId("test");
        adminAddDto.setAdminPwd(passwordEncoder.encode("test!"));
        adminAddDto.setAdminAuth(AdminAuth.MASTER);
        adminAddDto.setAdminStatus(AdminStatus.ACTIVE);

        Admin admin = adminRepository.findByAdminId(adminAddDto.getAdminId())
                .orElseGet(() -> adminRepository.save(adminAddDto.toEntity()));
        log.info("관리자 계정 생성 [아이디: {}], [패스워드: {}], [권한: {}], [상태: {}]",
                admin.getAdminId(), admin.getAdminPwd(), admin.getAdminAuth(), admin.getAdminStatus());
    }
}