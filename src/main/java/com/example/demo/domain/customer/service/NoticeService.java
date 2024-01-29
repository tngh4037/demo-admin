package com.example.demo.domain.customer.service;

import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.customer.domain.Notice;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findItems(NoticeSearchDto noticeSearchDto) {
        int count = noticeRepository.count(noticeSearchDto);
        noticeSearchDto.setTotalRecordCount(count);
        return noticeRepository.findAll(noticeSearchDto);
    }

    public Optional<Notice> findById(Integer noticeNo) {
        return noticeRepository.findById(noticeNo);
    }

    public void save(Notice notice) {
        noticeRepository.save(notice);
    }

    public void deleteByIds(Integer[] noticeNoList) {
        for (Integer noticeNo : noticeNoList) {
            noticeRepository.deleteById(noticeNo);
        }
    }
}
