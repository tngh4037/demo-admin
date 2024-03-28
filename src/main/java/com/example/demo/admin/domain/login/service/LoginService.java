package com.example.demo.admin.domain.login.service;

import com.example.demo.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminRepository adminRepository;

    public void loginFail(Integer adminNo) {
        adminRepository.updateFailCnt(adminNo, 1);
    }

    public void loginSuccess(Integer adminNo) {
        adminRepository.updateByLogin(adminNo);
    }
}