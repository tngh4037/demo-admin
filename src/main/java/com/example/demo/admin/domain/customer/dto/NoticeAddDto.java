package com.example.demo.admin.domain.customer.dto;

import com.example.demo.admin.domain.customer.define.NoticeType;
import com.example.demo.admin.domain.customer.domain.Notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoticeAddDto {

    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String contents;

    @NotBlank(message = "노출 여부를 선택해 주세요.")
    @Pattern(regexp = "[Y|N]", message = "노출 여부를 다시 선택해 주세요.")
    private String displayYn;

    @NotNull(message = "공지 유형을 선택해 주세요.")
    private NoticeType noticeType;

    private List<MultipartFile> uploadFiles;

    public Notice toEntity() {
        return Notice.of()
                .title(this.title)
                .contents(this.contents)
                .displayYn(this.displayYn)
                .noticeType(this.noticeType)
                .build();
    }

    public static Notice initForm() {
        return Notice.of()
                .displayYn("N")
                .build();
    }

    public List<MultipartFile> getFilesIfNotEmpty() {
        if (uploadFiles == null || uploadFiles.isEmpty() || (uploadFiles.size() == 1 && uploadFiles.get(0).isEmpty())) {
            return null;
        }

        return uploadFiles;
    }
}