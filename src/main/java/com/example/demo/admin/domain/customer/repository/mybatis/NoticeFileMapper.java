package com.example.demo.admin.domain.customer.repository.mybatis;

import com.example.demo.admin.domain.customer.domain.NoticeFile;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeFileMapper {
    List<NoticeFile> findAll(Integer noticeNo);
    void save(NoticeFile noticeFile);
    void delete(Integer noticeNo);
    void deleteById(Integer noticeFileNo);
}