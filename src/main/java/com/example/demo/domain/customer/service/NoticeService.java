package com.example.demo.domain.customer.service;

import com.example.demo.domain.customer.dto.NoticeAddDto;
import com.example.demo.domain.customer.dto.NoticeEditDto;
import com.example.demo.domain.customer.dto.NoticeSearchDto;
import com.example.demo.domain.customer.repository.NoticeRepository;
import com.example.demo.global.error.exception.DataNotFoundException;
import com.example.demo.global.utils.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.customer.domain.Notice;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findItems(NoticeSearchDto noticeSearchDto, PaginationDto paginationDto) {
        List<Notice> notices = new ArrayList<>();
        int count = noticeRepository.count(noticeSearchDto);
        if (count > 0) {
            paginationDto.setTotalRecordCount(count);
            notices = noticeRepository.findAll(noticeSearchDto, paginationDto);
        }

        return notices;
    }

    public Notice findById(Integer noticeNo) {
        return noticeRepository.findById(noticeNo).orElseThrow(DataNotFoundException::new);
    }

    public Notice save(NoticeAddDto noticeAddDto) {
        return noticeRepository.save(noticeAddDto.toEntity());
    }

    public void update(Integer noticeNo, NoticeEditDto noticeEditDto) {
        noticeRepository.update(noticeNo, noticeEditDto.toEntity());
    }

    @Transactional
    public void remove(Integer[] noticeNos) {
        for (Integer noticeNo : noticeNos) {
            noticeRepository.deleteById(noticeNo);
        }
    }
}
