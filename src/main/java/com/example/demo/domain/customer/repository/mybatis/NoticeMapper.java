package com.example.demo.domain.customer.repository.mybatis;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoticeMapper {
    List<Notice> findAll(NoticeSearchDto noticeSearchDto);
    Optional<Notice> findById(Integer noticeNo);
    void save(Notice notice);
    void deleteById(Integer noticeNo);
}