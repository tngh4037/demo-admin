package com.example.demo.domain.admin.service;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminAddDto;
import com.example.demo.domain.admin.dto.AdminEditDto;
import com.example.demo.domain.admin.dto.AdminSearchDto;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.global.common.PaginationDto;
import com.example.demo.global.error.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return adminRepository.findByAdminNo(adminNo)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 계정입니다."));
    }

    public Admin save(AdminAddDto adminAddDto) {
        return adminRepository.save(adminAddDto.toEntity());
    }

    public void update(Integer adminNo, AdminEditDto adminEditDto) {
        findByAdminNo(adminNo);
        adminRepository.update(adminNo, adminEditDto.toEntity());
    }

}
