package com.example.demo.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.repository.NoticeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getList() {
        return noticeRepository.getList();
    }

    public Notice getDetail(Integer noticeNo) {
        return noticeRepository.getDetail(noticeNo);
    }

    public void register(Notice notice) {
        noticeRepository.register(notice);
    }
}
