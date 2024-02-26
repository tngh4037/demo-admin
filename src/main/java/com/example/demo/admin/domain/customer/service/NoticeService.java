package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.dto.NoticeAddDto;
import com.example.demo.admin.domain.customer.dto.NoticeEditDto;
import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;
import com.example.demo.admin.domain.customer.exception.NoticeDuplicateException;
import com.example.demo.admin.domain.customer.exception.NoticeNotFoundException;
import com.example.demo.admin.domain.customer.repository.NoticeRepository;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.common.define.Yn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.demo.admin.domain.customer.domain.Notice;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeFileService noticeFileService;
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
        return noticeRepository.findById(noticeNo).orElseThrow(NoticeNotFoundException::new);
    }

    @Transactional
    public Notice save(NoticeAddDto noticeAddDto) {
        checkDuplicate(null, noticeAddDto.getTitle());
        Notice notice = noticeRepository.save(noticeAddDto.toEntity());
        noticeFileService.saveFiles(notice.getNoticeNo(), noticeAddDto.getUploadFiles());
        return notice;
    }

    @Transactional
    public void update(Integer noticeNo, NoticeEditDto noticeEditDto) {
        findById(noticeNo);
        checkDuplicate(noticeNo, noticeEditDto.getTitle());
        noticeRepository.update(noticeNo, noticeEditDto.toEntity());
        noticeFileService.updateFiles(noticeNo, noticeEditDto.getUploadFiles());
    }

    public void updateDisplayYn(Integer noticeNo, Yn displayYn) {
        noticeRepository.updateDisplayYn(noticeNo, displayYn);
    }

    private void checkDuplicate(Integer noticeNo, String title) {
        if (noticeRepository.isDuplicate(noticeNo, title)) {
            throw new NoticeDuplicateException();
        }
    }

    @Transactional
    public void remove(Integer[] noticeNos) {
        for (Integer noticeNo : noticeNos) {
            noticeRepository.deleteById(noticeNo);
            noticeFileService.removeFiles(noticeNo);
        }
    }
}
