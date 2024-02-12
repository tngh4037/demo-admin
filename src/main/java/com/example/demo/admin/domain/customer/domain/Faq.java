package com.example.demo.admin.domain.customer.domain;

import com.example.demo.admin.global.common.define.Yn;
import com.example.demo.admin.domain.customer.define.FaqType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Faq {
    private Integer faqNo;
    private String question;
    private String answer;
    private FaqType faqType;
    private Yn displayYn;
    private Yn displayTopYn;
    private Integer hits;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Faq(Integer faqNo, String question, String answer, FaqType faqType, Yn displayYn, Yn displayTopYn,
               Integer hits, LocalDateTime regDt, LocalDateTime modDt) {
        this.faqNo = faqNo;
        this.question = question;
        this.answer = answer;
        this.faqType = faqType;
        this.displayYn = displayYn;
        this.displayTopYn = displayTopYn;
        this.hits = hits;
        this.regDt = regDt;
        this.modDt = modDt;
    }
}