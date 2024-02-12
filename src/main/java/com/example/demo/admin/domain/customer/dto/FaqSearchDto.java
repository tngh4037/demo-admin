package com.example.demo.admin.domain.customer.dto;

import com.example.demo.admin.global.common.define.Yn;
import com.example.demo.admin.domain.customer.define.FaqType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FaqSearchDto {

    @Positive(message = "페이지 번호를 다시 확인해 주세요.")
    private int pageNo = 1;

    @Size(max = 20, message = "질문은 최대 20자까지 검색 가능합니다.")
    private String question;
    private FaqType faqType;
    private Yn displayYn;
    private Yn displayTopYn;
}