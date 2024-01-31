package com.example.demo.domain.customer.repository.mybatis;

import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.repository.NoticeRepository;
import com.example.demo.global.utils.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisNoticeRepository implements NoticeRepository {

    private final NoticeMapper noticeMapper;

    @Override
    public int count(NoticeSearchDto noticeSearchDto) {
        return noticeMapper.count(noticeSearchDto);
    }

    @Override
    public List<Notice> findAll(NoticeSearchDto noticeSearchDto, PaginationDto paginationDto) {
        return noticeMapper.findAll(noticeSearchDto, paginationDto);
    }

    @Override
    public Optional<Notice> findById(Integer noticeNo) {
        return noticeMapper.findById(noticeNo);
    }

    @Override
    public Notice save(Notice notice) {
        noticeMapper.save(notice);
        return notice;
    }

    @Override
    public void update(Integer noticeNo, Notice notice) {
        noticeMapper.update(noticeNo, notice);
    }

    @Override
    public void deleteById(Integer noticeNo) {
        noticeMapper.deleteById(noticeNo);
    }

}
