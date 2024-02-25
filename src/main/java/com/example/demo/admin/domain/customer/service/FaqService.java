package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.define.FaqType;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.dto.FaqSearchDto;
import com.example.demo.admin.domain.customer.exception.FaqNotFoundException;
import com.example.demo.admin.domain.customer.exception.FaqPolicyException;
import com.example.demo.admin.domain.customer.repository.FaqRepository;
import com.example.demo.admin.global.common.define.Yn;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.util.MessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {

    private static final int ACTIVE_DISPLAY_TOP_MAX_COUNT = 3;

    private final FaqRepository faqRepository;

    public List<Faq> findItems(FaqSearchDto faqSearchDto, PaginationDto paginationDto) {
        List<Faq> faqs = new ArrayList<>();
        int count = faqRepository.count(faqSearchDto);
        if (count > 0) {
            paginationDto.setTotalRecordCount(count);
            faqs = faqRepository.findAll(faqSearchDto, paginationDto);
        }

        return faqs;
    }

    public Faq findById(Integer faqNo) {
        return faqRepository.findById(faqNo).orElseThrow(FaqNotFoundException::new);
    }

    public Faq save(FaqAddDto faqAddDto) {
        checkActiveDisplayTopMaxCount(null, faqAddDto.getDisplayTopYn(), faqAddDto.getFaqType());
        return faqRepository.save(faqAddDto.toEntity());
    }

    public void update(Integer faqNo, FaqEditDto faqEditDto) {
        findById(faqNo);
        checkActiveDisplayTopMaxCount(faqNo, faqEditDto.getDisplayTopYn(), faqEditDto.getFaqType());
        faqRepository.update(faqNo, faqEditDto.toEntity());
        // TODO :: remove before upload file
    }

    private void checkActiveDisplayTopMaxCount(Integer faqNo, Yn displayTopYn, FaqType faqType) {
        if (displayTopYn == Yn.NO) return;

        int count = faqRepository.countForActiveDisplayTop(faqNo, faqType);
        if (count >= ACTIVE_DISPLAY_TOP_MAX_COUNT) {
            throw new FaqPolicyException(MessageHelper.getMessage("customer.faq.invalid.active.display.max.count",
                    new Object[]{ACTIVE_DISPLAY_TOP_MAX_COUNT}));
        }
    }

    @Transactional
    public void remove(List<Integer> faqNos) {
        for (Integer faqNo : faqNos) {
            faqRepository.deleteById(faqNo);
            // TODO :: remove before upload file
        }
    }
}