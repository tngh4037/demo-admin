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
        noticeRepository.register(Notice.register("제목1", "내용1", NoticeType.NOTICE));
        noticeRepository.register(Notice.register("제목2", "내용2", NoticeType.EVENT));
        noticeRepository.register(Notice.register("제목3", "내용3", NoticeType.GOODS));
        noticeRepository.register(Notice.register("제목4", "내용4", NoticeType.USER));
        noticeRepository.register(Notice.register("제목5", "내용5", NoticeType.ETC));
    }
}