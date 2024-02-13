package com.example.demo.admin.global.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadFile {

    private String originalFilename;
    private String storeFileName;
    private String fullPath;

    public UploadFile(String originalFilename, String storeFileName, String fullPath) {
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
        this.fullPath = fullPath;
    }
}