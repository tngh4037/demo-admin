package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.domain.Notice;
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
        for (int i = 1; i <= 201; i++) {
            noticeRepository.save(Notice.register("제목" + i, "내용" + i, NoticeType.NOTICE));
        }
    }
}