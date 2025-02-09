package com.example.demo.admin.domain.customer.repository.mybatis;

import com.example.demo.admin.domain.customer.define.FaqType;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqSearchDto;
import com.example.demo.admin.global.common.PaginationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

// 마이바티스 매핑 XML을 호출을 위한 매퍼 인터페이스 ( @Mapper 애노테이션을 적용해주어야 한다. 그래야 MyBatis 에서 인식할 수 있다. )
// Mybatis 에서는 이 인터페이스의 메서드를 호출하면, 매핑되는 xml(FaqMapper.xml)의 해당 SQL을 실행하고 결과를 돌려준다.
// 동작 원리: 마이바티스에서는 @Mapper가 적용된 인터페이스의 구현체를 생성(프록시 기술 적용)해서 스프링 빈으로 등록해준다. ( 그 구현체 내부에서 xml을 읽고 DB 처리함. ) )
@Mapper
public interface FaqMapper {
    int count(@Param("search") FaqSearchDto faqSearchDto);
    List<Faq> findAll(@Param("search") FaqSearchDto faqSearchDto, @Param("page") PaginationDto paginationDto);
    Optional<Faq> findById(Integer faqNo);
    int countForActiveDisplayTop(@Param("faqNo") Integer faqNo, @Param("faqType") FaqType faqType);
    void save(Faq faq);
    void update(@Param("faqNo") Integer faqNo, @Param("faq") Faq faq);
    void deleteById(Integer faqNo);
}

// 참고) 파라미터가 하나인 경우 @Param 생략 가능
//   ㄴ 참고로 그 경우, xml 에서는 아무렇게나 받을 수 있다. (#{param}, #{no}, #{idx}, ...)