package com.example.demo.domain.customer.dto;

import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.global.util.PaginationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSearchDto extends PaginationDto {
    private String title;
    private NoticeType noticeType;
}