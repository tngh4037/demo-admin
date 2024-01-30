package com.example.demo;

import com.example.demo.domain.customer.dto.NoticeAddDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.repository.NoticeRepository;

@Slf4j
public class DataInit {

    private final NoticeRepository noticeRepository;

    public DataInit(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("초기 데이터 세팅");
        /*
        for (int i = 1; i <= 201; i++) {
            NoticeAddDto noticeAddDto = new NoticeAddDto();
            noticeAddDto.setTitle("제목" + i);
            noticeAddDto.setContents("내용" + i);
            noticeAddDto.setDisplayYn("Y");
            noticeAddDto.setNoticeType(NoticeType.NOTICE);
            noticeRepository.save(noticeAddDto.toEntity());
        }
        */
    }
}