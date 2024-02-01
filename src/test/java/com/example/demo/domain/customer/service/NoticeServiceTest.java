package com.example.demo.domain.customer.service;

import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeAddDto;
import com.example.demo.domain.customer.exception.NoticeDuplicateException;
import com.example.demo.global.error.exception.DataNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("공지글 상세 조회")
    void findById() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle("TEST1");
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);
        Notice notice = noticeService.save(noticeAddDto);

        // when
        Notice findNotice = noticeService.findById(notice.getNoticeNo());

        // then
        Assertions.assertThat(findNotice.getNoticeNo()).isNotNull();
    }

    @Test
    @DisplayName("공지글 상세 조회시 공지글이 존재하지 않는 경우 예외가 발생한다")
    void findById_throw_if_not_exist() {
        // given
        int noticeNo = 0;

        // when & then
        Assertions.assertThatThrownBy(() -> noticeService.findById(noticeNo))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    @DisplayName("공지글 등록")
    void save() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle("TEST1");
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);

        // when
        Notice save = noticeService.save(noticeAddDto);

        // then
        Assertions.assertThat(save.getNoticeNo()).isGreaterThan(0);
    }

    @Test
    @DisplayName("공지글 등록시 중복된 제목은 등록할 수 없다")
    void save_throw_if_exist_duplicate_title() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle("TEST1");
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);

        // when
        Notice save = noticeService.save(noticeAddDto);

        // then
        Assertions.assertThatThrownBy(() -> noticeService.save(noticeAddDto))
                .isInstanceOf(NoticeDuplicateException.class);
    }

}