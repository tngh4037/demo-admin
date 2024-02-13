package com.example.demo.admin.global.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadFile {

    private String originalFilename;
    private String uploadFileName;
    private String uploadPath;

    public UploadFile(String originalFilename, String uploadFileName, String uploadPath) {
        this.originalFilename = originalFilename;
        this.uploadFileName = uploadFileName;
        this.uploadPath = uploadPath;
    }
}