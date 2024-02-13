package com.example.demo.admin.domain.customer.repository.mybatis;

import com.example.demo.admin.domain.customer.domain.NoticeFile;
import com.example.demo.admin.domain.customer.repository.NoticeFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisNoticeFileRepository implements NoticeFileRepository {

    private final NoticeFileMapper noticeFileMapper;

    @Override
    public List<NoticeFile> findAll(Integer noticeNo) {
        return noticeFileMapper.findAll(noticeNo);
    }

    @Override
    public NoticeFile save(NoticeFile noticeFile) {
        noticeFileMapper.save(noticeFile);
        return noticeFile;
    }

    @Override
    public void delete(Integer noticeNo) {
        noticeFileMapper.delete(noticeNo);
    }

    @Override
    public void deleteById(Integer noticeFileNo) {
        noticeFileMapper.deleteById(noticeFileNo);
    }
}