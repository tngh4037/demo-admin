package com.example.demo.domain.customer.dto;

import com.example.demo.domain.customer.define.NoticeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSearchDto {
    private String title;
    private NoticeType noticeType;
}