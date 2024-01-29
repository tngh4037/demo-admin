package com.example.demo.domain.customer.repository;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {

    int count(NoticeSearchDto noticeSearchDto);
    List<Notice> findAll(NoticeSearchDto noticeSearchDto);
    Optional<Notice> findById(Integer noticeNo);
    void save(Notice notice);
    void deleteById(Integer noticeNo);

}
