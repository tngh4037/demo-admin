package com.example.demo.admin.domain.customer.repository;

import com.example.demo.admin.domain.customer.define.FaqType;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqSearchDto;
import com.example.demo.admin.global.common.PaginationDto;

import java.util.List;
import java.util.Optional;

public interface FaqRepository {
    int count(FaqSearchDto faqSearchDto);
    List<Faq> findAll(FaqSearchDto faqSearchDto, PaginationDto paginationDto);
    Optional<Faq> findById(Integer faqNo);
    int countForActiveDisplayTop(Integer faqNo, FaqType faqType);
    Faq save(Faq faq);
    void update(Integer faqNo, Faq faq);
    void deleteById(Integer faqNo);
}