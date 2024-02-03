package com.example.demo.domain.customer.service;

import com.example.demo.domain.customer.domain.Faq;
import com.example.demo.domain.customer.dto.FaqAddDto;
import com.example.demo.domain.customer.dto.FaqEditDto;
import com.example.demo.domain.customer.dto.FaqSearchDto;
import com.example.demo.domain.customer.repository.FaqRepository;
import com.example.demo.global.error.exception.DataNotFoundException;
import com.example.demo.global.utils.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {

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
        return faqRepository.findById(faqNo).orElseThrow(DataNotFoundException::new);
    }

    public Faq save(FaqAddDto faqAddDto) {
        return faqRepository.save(faqAddDto.toEntity());
    }

    public void update(Integer faqNo, FaqEditDto faqEditDto) {
        faqRepository.update(faqNo, faqEditDto.toEntity());
    }

    @Transactional
    public void remove(Integer[] faqNos) {
        for (Integer faqNo : faqNos) {
            faqRepository.deleteById(faqNo);
        }
    }
}