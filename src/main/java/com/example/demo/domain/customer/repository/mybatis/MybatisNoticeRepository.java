package com.example.demo.domain.customer.repository.mybatis;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisNoticeRepository implements NoticeRepository {

    private final NoticeMapper noticeMapper;

    @Override
    public List<Notice> findAll(NoticeSearchDto noticeSearchDto) {
        return noticeMapper.findAll(noticeSearchDto);
    }

    @Override
    public Optional<Notice> findById(Integer noticeNo) {
        return noticeMapper.findById(noticeNo);
    }

    @Override
    public void save(Notice notice) {
        noticeMapper.save(notice);
    }

    @Override
    public void deleteById(Integer noticeNo) {
        noticeMapper.deleteById(noticeNo);
    }
}
