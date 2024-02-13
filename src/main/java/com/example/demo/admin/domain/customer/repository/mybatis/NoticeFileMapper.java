package com.example.demo.admin.domain.customer.repository.mybatis;

import com.example.demo.admin.domain.customer.domain.NoticeFile;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeFileMapper {
    void save(NoticeFile noticeFile);
}