package com.example.demo.domain.customer.domain;

import lombok.*;
import com.example.demo.domain.customer.define.NoticeType;

import java.time.LocalDateTime;

@Data
public class Notice {
    private Integer noticeNo;
    private String title;
    private String contents;
    private NoticeType noticeType;
    private Integer hits;
    private String displayYn;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    public static Notice register(String title, String contents, NoticeType noticeType) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContents(contents);
        notice.setNoticeType(noticeType);
        notice.setHits(0);
        notice.setDisplayYn("Y");
        notice.setRegDt(LocalDateTime.now());
        notice.setModDt(LocalDateTime.now());
        return notice;
    }
}
