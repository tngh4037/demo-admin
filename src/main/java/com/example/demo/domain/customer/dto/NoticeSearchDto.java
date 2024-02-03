package com.example.demo.domain.customer.dto;

import com.example.demo.domain.customer.define.NoticeType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeSearchDto {

    @Positive(message = "페이지 번호를 다시 확인해 주세요.")
    private int pageNo = 1;

    @Size(max = 50, message = "제목은 최대 50자까지 검색 가능합니다.")
    private String title;
    private NoticeType noticeType;
    private String displayYn;
}