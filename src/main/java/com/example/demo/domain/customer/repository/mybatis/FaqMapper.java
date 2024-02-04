package com.example.demo.domain.customer.repository.mybatis;

import com.example.demo.domain.customer.domain.Faq;
import com.example.demo.domain.customer.dto.FaqSearchDto;
import com.example.demo.global.common.PaginationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FaqMapper {
    int count(@Param("search") FaqSearchDto faqSearchDto);
    List<Faq> findAll(@Param("search") FaqSearchDto faqSearchDto, @Param("page") PaginationDto paginationDto);
    Optional<Faq> findById(Integer faqNo);
    void save(Faq faq);
    void update(@Param("faqNo") Integer faqNo, @Param("faq") Faq faq);
    void deleteById(Integer faqNo);
}