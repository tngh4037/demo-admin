package com.example.demo.admin.global.common.define;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileUploadType {
    CUSTOMER_NOTICE("C:/demo-admin/customer/notice/", 3, 1048576L, new String[]{"pdf", "xls", "xlsx", "jpg", "jpeg", "png"});

    private final String fileDir;
    private final Integer maxCount;
    private final Long maxSize;
    private final String[] extensions;

    public String getUploadPath(String uploadFileName) {
        return fileDir + uploadFileName;
    }
}