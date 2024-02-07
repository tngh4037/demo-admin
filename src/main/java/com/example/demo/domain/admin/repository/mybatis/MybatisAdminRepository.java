package com.example.demo.domain.admin.repository.mybatis;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminSearchDto;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.global.common.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisAdminRepository implements AdminRepository {

    private final AdminMapper adminMapper;

    @Override
    public int count(AdminSearchDto adminSearchDto) {
        return adminMapper.count(adminSearchDto);
    }

    @Override
    public List<Admin> findAll(AdminSearchDto adminSearchDto, PaginationDto paginationDto) {
        return adminMapper.findAll(adminSearchDto, paginationDto);
    }

    @Override
    public Optional<Admin> findByAdminNo(Integer adminNo) {
        return adminMapper.findByAdminNo(adminNo);
    }

    @Override
    public Optional<Admin> findByAdminId(String adminId) {
        return adminMapper.findByAdminId(adminId);
    }

    @Override
    public Admin save(Admin admin) {
        adminMapper.save(admin);
        return admin;
    }

    @Override
    public void update(Integer adminNo, Admin admin) {
        adminMapper.update(adminNo, admin);
    }

    @Override
    public void updateLoginDt(Integer adminNo) {
        adminMapper.updateLoginDt(adminNo);
    }

    @Override
    public void updateFailCnt(Integer adminNo, Integer count) {
        adminMapper.updateFailCnt(adminNo, count);
    }

    @Override
    public void updateByLogin(Integer adminNo) {
        adminMapper.updateByLogin(adminNo);
    }

}