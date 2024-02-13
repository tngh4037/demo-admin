package com.example.demo.admin.domain.customer.repository;

import com.example.demo.admin.domain.customer.domain.NoticeFile;

import java.util.List;

public interface NoticeFileRepository {
    List<NoticeFile> findAll(Integer noticeNo);
    NoticeFile save(NoticeFile noticeFile);
    void delete(Integer noticeNo);
    void deleteById(Integer noticeFileNo);
}