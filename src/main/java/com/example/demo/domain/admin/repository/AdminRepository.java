package com.example.demo.domain.admin.repository;

import com.example.demo.domain.admin.domain.Admin;

import java.util.Optional;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findByLoginId(String adminId);
}
