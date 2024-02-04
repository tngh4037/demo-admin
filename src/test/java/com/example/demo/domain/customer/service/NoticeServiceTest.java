package com.example.demo.domain.customer.service;

import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.domain.Notice;
import com.example.demo.domain.customer.dto.NoticeAddDto;
import com.example.demo.domain.customer.dto.NoticeEditDto;
import com.example.demo.domain.customer.exception.NoticeDuplicateException;
import com.example.demo.global.error.exception.DataNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = "local")
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("공지글 조회")
    void findById() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle(LocalDateTime.now().toString());
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
    @DisplayName("공지글 조회 - 공지글이 존재하지 않는 경우 예외가 발생한다")
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
        noticeAddDto.setTitle(LocalDateTime.now().toString());
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);

        // when
        Notice save = noticeService.save(noticeAddDto);

        // then
        Assertions.assertThat(save.getNoticeNo()).isGreaterThan(0);
    }

    @Test
    @DisplayName("공지글 등록 - 기존에 등록된 공지글과 동일한 제목은 등록할 수 없다.")
    void save_throw_duplicate_title() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle(LocalDateTime.now().toString());
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);

        // when
        noticeService.save(noticeAddDto);

        // then
        Assertions.assertThatThrownBy(() -> noticeService.save(noticeAddDto))
                .isInstanceOf(NoticeDuplicateException.class);
    }

    @Test
    @DisplayName("공지글 수정")
    void update() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle(LocalDateTime.now().toString());
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);
        Notice new1 = noticeService.save(noticeAddDto);

        NoticeEditDto noticeEditDto = new NoticeEditDto();
        noticeEditDto.setTitle(LocalDateTime.now().toString());
        noticeEditDto.setContents("TEST");
        noticeEditDto.setDisplayYn("Y");
        noticeEditDto.setNoticeType(NoticeType.NOTICE);

        // when & then
        noticeService.update(new1.getNoticeNo(), noticeEditDto);
    }

    @Test
    @DisplayName("공지글 수정 - 기존에 등록된 공지글과 동일한 제목은 등록할 수 없다.")
    void update_throw_duplicate_title() {
        // given
        LocalDateTime title = LocalDateTime.now();

        NoticeAddDto noticeAddDto1 = new NoticeAddDto();
        noticeAddDto1.setTitle(title.toString());
        noticeAddDto1.setContents("TEST");
        noticeAddDto1.setDisplayYn("Y");
        noticeAddDto1.setNoticeType(NoticeType.NOTICE);
        Notice new1 = noticeService.save(noticeAddDto1);
        NoticeAddDto noticeAddDto2 = new NoticeAddDto();
        noticeAddDto2.setTitle(title.plusDays(1).toString());
        noticeAddDto2.setContents("TEST");
        noticeAddDto2.setDisplayYn("Y");
        noticeAddDto2.setNoticeType(NoticeType.NOTICE);
        Notice new2 = noticeService.save(noticeAddDto2);

        NoticeEditDto new2Edit = new NoticeEditDto();
        new2Edit.setTitle(title.toString());
        new2Edit.setContents("TEST");
        new2Edit.setDisplayYn("Y");
        new2Edit.setNoticeType(NoticeType.NOTICE);

        // when & then
        Assertions.assertThatThrownBy(() -> noticeService.update(new2.getNoticeNo(), new2Edit))
                .isInstanceOf(NoticeDuplicateException.class);
    }

    @Test
    @DisplayName("공지글 삭제")
    void remove() {
        // given
        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle(LocalDateTime.now().toString());
        noticeAddDto.setContents("TEST");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);
        Notice notice = noticeService.save(noticeAddDto);

        // when
        Integer[] noticeNos = new Integer[]{notice.getNoticeNo()};
        noticeService.remove(noticeNos);

        // then
        Assertions.assertThatThrownBy(() -> noticeService.findById(notice.getNoticeNo()))
                .isInstanceOf(DataNotFoundException.class);
    }
}