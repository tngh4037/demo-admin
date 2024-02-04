package com.example.demo.domain.customer.repository;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.global.common.PaginationDto;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {
    int count(NoticeSearchDto noticeSearchDto);
    List<Notice> findAll(NoticeSearchDto noticeSearchDto, PaginationDto paginationDto);
    Optional<Notice> findById(Integer noticeNo);
    Notice save(Notice notice);
    void update(Integer noticeNo, Notice notice);
    void deleteById(Integer noticeNo);
}