package com.example.demo.domain.customer.dto;

import com.example.demo.domain.customer.define.NoticeType;
import com.example.demo.domain.customer.domain.Notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeAddDto {
    private String title;
    private String contents;
    private String displayYn;
    private NoticeType noticeType;

    public Notice toEntity() {
        return Notice.of()
                .title(this.title)
                .contents(this.contents)
                .displayYn(this.displayYn)
                .noticeType(this.noticeType)
                .build();
    }
}