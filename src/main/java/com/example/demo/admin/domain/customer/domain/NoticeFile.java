package com.example.demo.admin.domain.customer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile {
    private Integer noticeFileNo;
    private Integer noticeNo;
    private String orgFileName;
    private String fileName;
    private LocalDateTime regDt;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public NoticeFile(Integer noticeFileNo, Integer noticeNo,
                      String orgFileName, String fileName, LocalDateTime regDt) {
        this.noticeFileNo = noticeFileNo;
        this.noticeNo = noticeNo;
        this.orgFileName = orgFileName;
        this.fileName = fileName;
        this.regDt = regDt;
    }
}
