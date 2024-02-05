package com.example.demo.domain.admin.repository.mybatis;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisAdminRepository implements AdminRepository {

    private final AdminMapper adminMapper;

    @Override
    public Optional<Admin> findByLoginId(String adminId) {
        return adminMapper.findByLoginId(adminId);
    }

    @Override
    public Admin save(Admin admin) {
        adminMapper.save(admin);
        return admin;
    }
}