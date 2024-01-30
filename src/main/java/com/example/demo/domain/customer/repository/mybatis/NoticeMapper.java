package com.example.demo.domain.customer.repository.mybatis;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoticeMapper {
    int count(NoticeSearchDto noticeSearchDto);
    List<Notice> findAll(NoticeSearchDto noticeSearchDto);
    Optional<Notice> findById(Integer noticeNo);
    void save(Notice notice);
    void update(@Param("noticeNo") Integer noticeNo, @Param("notice") Notice notice);
    void deleteById(Integer noticeNo);
}