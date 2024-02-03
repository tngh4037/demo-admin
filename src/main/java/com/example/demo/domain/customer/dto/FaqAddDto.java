package com.example.demo.domain.customer.dto;

import com.example.demo.domain.common.define.Yn;
import com.example.demo.domain.customer.define.FaqType;
import com.example.demo.domain.customer.domain.Faq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FaqAddDto {

    @NotBlank(message = "질문을 입력해 주세요.")
    @Size(max = 100, message = "질문은 최대 100자까지 입력 가능합니다.")
    private String question;

    @NotBlank(message = "답변을 입력해 주세요.")
    private String answer;

    @NotNull(message = "질문 유형을 선택해 주세요.")
    private FaqType faqType;
    
    @NotNull(message = "노출 여부를 선택해 주세요.")
    private Yn displayYn;

    @NotNull(message = "상단 노출 여부를 선택해 주세요.")
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

    public static Faq initForm() {
        return Faq.of()
                .displayYn(Yn.NO)
                .displayTopYn(Yn.NO)
                .build();
    }
}