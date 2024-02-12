package com.example.demo.admin.domain.customer.domain;

import lombok.*;
import com.example.demo.admin.domain.customer.define.NoticeType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    private Integer noticeNo;
    private String title;
    private String contents;
    private NoticeType noticeType;
    private String displayYn;
    private Integer hits;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Notice(Integer noticeNo, String title, String contents, NoticeType noticeType, String displayYn,
                  Integer hits, LocalDateTime regDt, LocalDateTime modDt) {
        this.noticeNo = noticeNo;
        this.title = title;
        this.contents = contents;
        this.noticeType = noticeType;
        this.displayYn = displayYn;
        this.hits = hits;
        this.regDt = regDt;
        this.modDt = modDt;
    }
}
