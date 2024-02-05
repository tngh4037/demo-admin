package com.example.demo.domain.admin.repository.mybatis;

import com.example.demo.domain.admin.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AdminMapper {
    void save(Admin admin);
    Optional<Admin> findByLoginId(String adminId);
}