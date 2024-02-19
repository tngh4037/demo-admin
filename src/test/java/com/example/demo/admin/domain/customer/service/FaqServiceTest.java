package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.define.FaqType;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.exception.FaqNotFoundException;
import com.example.demo.admin.domain.customer.exception.FaqPolicyException;
import com.example.demo.admin.global.common.define.Yn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = "local")
class FaqServiceTest {

    private final int ACTIVE_DISPLAY_TOP_MAX_COUNT = 3;
    private FaqAddDto faqAddDto;
    private FaqEditDto faqEditDto;

    @Autowired
    FaqService faqService;

    @BeforeEach
    void beforeEach() {
        faqAddDto = new FaqAddDto();
        faqAddDto.setQuestion("질문입니다.");
        faqAddDto.setAnswer("답변입니다.");
        faqAddDto.setFaqType(FaqType.DELIVER);
        faqAddDto.setDisplayYn(Yn.NO);
        faqAddDto.setDisplayTopYn(Yn.NO);

        faqEditDto = new FaqEditDto();
        faqEditDto.setQuestion("질문입니다.");
        faqEditDto.setAnswer("답변입니다.");
        faqEditDto.setFaqType(FaqType.BUY);
        faqEditDto.setDisplayYn(Yn.NO);
        faqEditDto.setDisplayTopYn(Yn.NO);
    }

    @Test
    @DisplayName("자주하는질문 조회")
    void findById() {
        // given
        Faq save = faqService.save(faqAddDto);

        // when
        Faq faq = faqService.findById(save.getFaqNo());

        // then
        Assertions.assertThat(faq.getFaqNo()).isNotNull().isGreaterThan(0);
    }

    @Test
    @DisplayName("자주하는질문 조회 - 게시글이 존재하지 않는 경우 예외가 발생한다.")
    void findById_throw_if_not_exist() {
        // given
        int faqNo = 0;

        // when & then
        Assertions.assertThatThrownBy(() -> faqService.findById(faqNo))
                .isInstanceOf(FaqNotFoundException.class);
    }

    @Test
    @DisplayName("자주하는질문 등록")
    void save() {
        // given & when
        Faq save = faqService.save(faqAddDto);

        // then
        Assertions.assertThat(save.getFaqNo()).isNotNull().isGreaterThan(0);
    }

    @Test
    @DisplayName("자주하는질문 등록 - 질문 유형별 상단 노출 가능한 게시글은 " + ACTIVE_DISPLAY_TOP_MAX_COUNT + "개를 넘을 수 없다.")
    void save_throw_if_over_max_active_display_top() {
        // given
        faqAddDto.setDisplayTopYn(Yn.YES);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            for (int i = 0; i < ACTIVE_DISPLAY_TOP_MAX_COUNT + 1; i++) {
                faqService.save(faqAddDto);
            }
        }).isInstanceOf(FaqPolicyException.class);
    }

    @Test
    @DisplayName("자주하는질문 수정")
    void update() {
        // given
        Faq save = faqService.save(faqAddDto);

        // when
        faqService.update(save.getFaqNo(), faqEditDto);

        // then
        Assertions.assertThat(faqService.findById(save.getFaqNo()).getFaqType()).isEqualTo(FaqType.BUY);
    }

    @Test
    @DisplayName("자주하는질문 수정 - 질문 유형별 상단 노출 가능한 게시글은 " + ACTIVE_DISPLAY_TOP_MAX_COUNT + "개를 넘을 수 없다.")
    void update_throw_if_over_max_active_display_top() {
        // given & when & then
        List<Faq> list = new ArrayList<>();
        for (int i = 0; i < ACTIVE_DISPLAY_TOP_MAX_COUNT + 1; i++) {
            list.add(faqService.save(faqAddDto));
        }
        Assertions.assertThatThrownBy(() -> {
            for (Faq faq : list) {
                faqEditDto.setDisplayTopYn(Yn.YES);
                faqService.update(faq.getFaqNo(), faqEditDto);
            }
        }).isInstanceOf(FaqPolicyException.class);
    }

    @Test
    @DisplayName("자주하는질문 삭제")
    void remove() {
        // given
        Faq faq = faqService.save(faqAddDto);

        // when
        List<Integer> faqNos = new ArrayList<>();
        faqNos.add(faq.getFaqNo());
        faqService.remove(faqNos);

        // then
        Assertions.assertThatThrownBy(() -> faqService.findById(faq.getFaqNo()))
                .isInstanceOf(FaqNotFoundException.class);
    }
}