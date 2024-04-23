package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.define.NoticeType;
import com.example.demo.admin.domain.customer.domain.Notice;
import com.example.demo.admin.domain.customer.dto.NoticeAddDto;
import com.example.demo.admin.domain.customer.dto.NoticeEditDto;
import com.example.demo.admin.domain.customer.exception.NoticeDuplicateException;
import com.example.demo.admin.domain.customer.exception.NoticeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class NoticeServiceTest {

    private final String title = LocalDateTime.now().toString();
    private NoticeAddDto noticeAddDto;
    private NoticeEditDto noticeEditDto;

    @Autowired
    private NoticeService noticeService;

    @BeforeEach
    void beforeEach() {
        noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle(title);
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);

        noticeEditDto = new NoticeEditDto();
        noticeEditDto.setTitle(title);
        noticeEditDto.setContents("TEST");
        noticeEditDto.setDisplayYn("Y");
        noticeEditDto.setNoticeType(NoticeType.USER);
    }

    @Test
    @DisplayName("공지사항 조회")
    void findById() {
        // given
        Notice notice = noticeService.save(noticeAddDto);

        // when
        Notice findNotice = noticeService.findById(notice.getNoticeNo());

        // then
        assertThat(findNotice.getNoticeNo()).isNotNull().isGreaterThan(0);
    }

    @Test
    @DisplayName("공지사항 조회 - 게시글이 존재하지 않는 경우 예외가 발생한다.")
    void findById_throw_if_not_exist() {
        // given
        int noticeNo = 0;

        // when & then
        assertThatThrownBy(() -> noticeService.findById(noticeNo))
                .isInstanceOf(NoticeNotFoundException.class);
    }

    @Test
    @DisplayName("공지사항 등록")
    void save() {
        // given & when
        Notice save = noticeService.save(noticeAddDto);

        // then
        assertThat(save.getNoticeNo()).isNotNull().isGreaterThan(0);
    }

    @Test
    @DisplayName("공지사항 등록 - 기존에 등록된 다른 게시글과 동일한 제목은 등록할 수 없다.")
    void save_throw_duplicate_title() {
        // given & when
        noticeService.save(noticeAddDto);

        // then
        assertThatThrownBy(() -> noticeService.save(noticeAddDto))
                .isInstanceOf(NoticeDuplicateException.class);
    }

    @Test
    @DisplayName("공지사항 수정")
    void update() {
        // given
        Notice notice = noticeService.save(noticeAddDto);

        // when
        noticeService.update(notice.getNoticeNo(), noticeEditDto);

        // then
        assertThat(noticeService.findById(notice.getNoticeNo()).getNoticeType()).isEqualTo(NoticeType.USER);
    }

    @Test
    @DisplayName("공지사항 수정 - 기존에 등록된 다른 게시글과 동일한 제목은 등록할 수 없다.")
    void update_throw_duplicate_title() {
        // given
        Notice notice1 = noticeService.save(noticeAddDto);
        noticeAddDto.setTitle(title + title);
        Notice notice2 = noticeService.save(noticeAddDto);

        // when & then
        noticeEditDto.setTitle(title);
        assertThatThrownBy(() -> noticeService.update(notice2.getNoticeNo(), noticeEditDto))
                .isInstanceOf(NoticeDuplicateException.class);
    }

    @Test
    @DisplayName("공지사항 삭제")
    void remove() {
        // given
        Notice notice = noticeService.save(noticeAddDto);

        // when
        Integer[] noticeNos = new Integer[]{notice.getNoticeNo()};
        noticeService.remove(noticeNos);

        // then
        assertThatThrownBy(() -> noticeService.findById(notice.getNoticeNo()))
                .isInstanceOf(NoticeNotFoundException.class);
    }

    @Test
    void AopCheck() {
        log.info("noticeService class={}", noticeService.getClass());
        assertThat(AopUtils.isAopProxy(noticeService)).isTrue();
    }
}