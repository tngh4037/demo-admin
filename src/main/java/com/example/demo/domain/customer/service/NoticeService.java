package com.example.demo.domain.customer.service;

import com.example.demo.domain.customer.dto.NoticeAddDto;
import com.example.demo.domain.customer.dto.NoticeEditDto;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.customer.domain.Notice;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findItems(NoticeSearchDto noticeSearchDto) {
        int count = noticeRepository.count(noticeSearchDto);
        noticeSearchDto.setTotalRecordCount(count);
        return noticeRepository.findAll(noticeSearchDto);
    }

    public Notice findById(Integer noticeNo) {
        return noticeRepository.findById(noticeNo).orElseThrow();
    }

    public Notice save(NoticeAddDto noticeAddDto) {
        return noticeRepository.save(noticeAddDto.toEntity());
    }

    public void update(Integer noticeNo, NoticeEditDto noticeEditDto) {
        noticeRepository.update(noticeNo, noticeEditDto.toEntity());
    }
}
