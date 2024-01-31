package com.example.demo.domain.customer.dto;

import com.example.demo.domain.customer.define.NoticeType;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSearchDto {

    private Integer pageNo = 1;

    @Size(max = 50, message = "제목은 최대 50자까지 검색 가능합니다.")
    private String title;
    private NoticeType noticeType;
    private String displayYn;
}