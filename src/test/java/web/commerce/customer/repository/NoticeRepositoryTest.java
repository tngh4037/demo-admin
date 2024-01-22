package web.commerce.customer.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.commerce.domain.customer.define.NoticeType;
import web.commerce.domain.customer.domain.Notice;
import web.commerce.domain.customer.repository.NoticeRepository;

@Slf4j
class NoticeRepositoryTest {

    private final NoticeRepository noticeRepository = new NoticeRepository();

    @AfterEach
    void clear() {
        noticeRepository.clear();
    }

    @Test
    @DisplayName("공지글 목록")
    void list() {
        // given
        Notice notice1 = Notice.register("제목1", "내용1", NoticeType.NOTICE);
        Notice notice2 = Notice.register("제목2", "내용2", NoticeType.ETC);

        // when
        noticeRepository.register(notice1);
        noticeRepository.register(notice2);

        // then
        Assertions.assertThat(noticeRepository.getList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("공지글 상세")
    void detail() {
        // given
        Notice notice = Notice.register("제목1", "내용1", NoticeType.NOTICE);

        // when
        noticeRepository.register(notice);

        // then
        Assertions.assertThat(noticeRepository.getDetail(notice.getNoticeNo())).isNotNull();
    }

    @Test
    @DisplayName("공지글 등록")
    void register() {
        // given
        Notice notice = Notice.register("제목1", "내용1", NoticeType.NOTICE);

        // when
        noticeRepository.register(notice);

        // then
        Assertions.assertThat(noticeRepository.getDetail(notice.getNoticeNo())).isNotNull();
    }
}