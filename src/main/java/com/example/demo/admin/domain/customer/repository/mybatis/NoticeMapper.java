package com.example.demo.admin.domain.customer.repository.mybatis;

import com.example.demo.admin.domain.customer.domain.Notice;
import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.common.define.Yn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoticeMapper {
    int count(@Param("search") NoticeSearchDto noticeSearchDto);
    List<Notice> findAll(@Param("search") NoticeSearchDto noticeSearchDto, @Param("page") PaginationDto paginationDto);
    Optional<Notice> findById(Integer noticeNo);
    boolean isDuplicate(@Param("noticeNo") Integer noticeNo, @Param("title") String title);
    void save(Notice notice);
    void update(@Param("noticeNo") Integer noticeNo, @Param("notice") Notice notice);
    void updateDisplayYn(@Param("noticeNo") Integer noticeNo, @Param("displayYn") Yn displayYn);
    void deleteById(Integer noticeNo);
}