package com.example.demo.domain.admin.dto;

import com.example.demo.domain.admin.define.AdminAuth;
import com.example.demo.domain.admin.define.AdminStatus;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminSearchDto {

    @Positive(message = "페이지 번호를 다시 확인해 주세요.")
    private int pageNo = 1;

    @Size(max = 16, message = "아이디는 최대 16자까지 검색 가능합니다.")
    private String adminId;
    
    private AdminAuth adminAuth;
    private AdminStatus adminStatus;
}
