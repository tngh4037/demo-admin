package com.example.demo.domain.customer.dto;

import com.example.demo.global.common.ValidationGroups;
import com.example.demo.global.common.define.Yn;
import com.example.demo.domain.customer.define.FaqType;
import com.example.demo.domain.customer.domain.Faq;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

// @JsonIgnoreProperties({"faqNo"})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FaqEditDto {

    @NotBlank(message = "질문을 입력해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    @Size(max = 100, message = "질문을 최대 100자까지 입력 가능합니다.", groups = ValidationGroups.SizeGroup.class)
    private String question;

    @NotBlank(message = "답변을 입력해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private String answer;

    @NotNull(message = "질문 유형을 선택해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private FaqType faqType;

    @NotNull(message = "노출 여부를 선택해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private Yn displayYn;

    @NotNull(message = "상단 노출 여부를 선택해 주세요.", groups = ValidationGroups.NotBlankGroup.class)
    private Yn displayTopYn;

    public Faq toEntity() {
        return Faq.of()
                .question(this.question)
                .answer(this.answer)
                .faqType(this.faqType)
                .displayYn(this.displayYn)
                .displayTopYn(this.displayTopYn)
                .build();
    }
}