package com.example.demo.admin.domain.customer.repository.mybatis;

import com.example.demo.admin.domain.customer.domain.NoticeFile;
import com.example.demo.admin.domain.customer.repository.NoticeFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisNoticeFileRepository implements NoticeFileRepository {

    private final NoticeFileMapper noticeFileMapper;

    @Override
    public NoticeFile save(NoticeFile noticeFile) {
        noticeFileMapper.save(noticeFile);
        return noticeFile;
    }
}