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
    private String storePath;

    public UploadFile(String originalFilename, String storeFileName, String storePath) {
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
        this.storePath = storePath;
    }
}