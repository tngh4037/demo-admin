package com.example.demo.domain.admin.repository.mybatis;

import com.example.demo.domain.admin.domain.Admin;
import com.example.demo.domain.admin.dto.AdminSearchDto;
import com.example.demo.global.common.PaginationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    int count(@Param("search") AdminSearchDto adminSearchDto);
    List<Admin> findAll(@Param("search") AdminSearchDto adminSearchDto, @Param("page") PaginationDto paginationDto);
    Optional<Admin> findByAdminNo(Integer adminNo);
    Optional<Admin> findByAdminId(String adminId);
    void save(Admin admin);
    void update(@Param("adminNo") Integer adminNo, @Param("admin") Admin admin);
}