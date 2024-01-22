package web.commerce.domain.customer.domain;

import lombok.*;
import web.commerce.domain.customer.define.NoticeType;

import java.time.LocalDateTime;

@Data
public class Notice {
    private Integer noticeNo;
    private String title;
    private String content;
    private NoticeType noticeType;
    private Integer hits;
    private String displayYn;
    private String topYn;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    public static Notice register(String title, String content, NoticeType noticeType) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setNoticeType(noticeType);
        notice.setHits(0);
        notice.setDisplayYn("Y");
        notice.setTopYn("N");
        notice.setRegDt(LocalDateTime.now());
        notice.setModDt(LocalDateTime.now());
        return notice;
    }
}
