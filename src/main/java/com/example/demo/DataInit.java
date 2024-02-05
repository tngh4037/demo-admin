package com.example.demo;

import com.example.demo.domain.admin.define.AdminAuth;
import com.example.demo.domain.admin.define.AdminStatus;
import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminAddDto;
import com.example.demo.domain.admin.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
public class DataInit {

    private final AdminRepository adminRepository;

    public DataInit(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        AdminAddDto adminAddDto = new AdminAddDto();
        adminAddDto.setAdminId("test");
        adminAddDto.setAdminPwd("test!");
        adminAddDto.setAdminAuth(AdminAuth.MASTER);
        adminAddDto.setAdminStatus(AdminStatus.ACTIVE);

        Admin admin = adminRepository.findByLoginId(adminAddDto.getAdminId())
                .orElseGet(() -> adminRepository.save(adminAddDto.toEntity()));
        log.info("관리자 계정 생성 [아이디: {}], [패스워드: {}], [권한: {}], [상태: {}]",
                admin.getAdminId(), admin.getAdminPwd(), admin.getAdminAuth(), admin.getAdminStatus());
    }
}