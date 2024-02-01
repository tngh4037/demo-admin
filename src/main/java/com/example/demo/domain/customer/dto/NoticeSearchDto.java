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

    private int pageNo = 1;

    @Size(max = 50, message = "제목은 최대 50자까지 검색 가능합니다.")
    private String title;
    private NoticeType noticeType;
    private String displayYn;

    public static NoticeSearchDto checkDuplicate(String title) {
        NoticeSearchDto noticeSearchDto = new NoticeSearchDto();
        noticeSearchDto.setTitle(title);
        noticeSearchDto.setDisplayYn("Y");
        return noticeSearchDto;
    }
}