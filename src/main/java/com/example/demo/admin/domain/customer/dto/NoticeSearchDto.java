package com.example.demo.admin.domain.customer.dto;

import com.example.demo.admin.domain.customer.define.NoticeType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoticeSearchDto {

    @Positive(message = "페이지 번호를 다시 확인해 주세요.")
    private int pageNo = 1;

    @Size(max = 50, message = "제목은 최대 50자까지 검색 가능합니다.")
    private String title;
    private NoticeType noticeType;
    private String displayYn;
}