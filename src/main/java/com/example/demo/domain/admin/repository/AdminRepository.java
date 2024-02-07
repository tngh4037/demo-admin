package com.example.demo.domain.admin.repository;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminSearchDto;
import com.example.demo.global.common.PaginationDto;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    int count(AdminSearchDto adminSearchDto);
    List<Admin> findAll(AdminSearchDto adminSearchDto, PaginationDto paginationDto);
    Optional<Admin> findByAdminNo(Integer adminNo);
    Optional<Admin> findByAdminId(String adminId);
    Admin save(Admin admin);
    void update(Integer adminNo, Admin admin);
    void updateLoginDt(Integer adminNo);
    void updateFailCnt(Integer adminNo, Integer count);
    void updateByLogin(Integer adminNo);
}
