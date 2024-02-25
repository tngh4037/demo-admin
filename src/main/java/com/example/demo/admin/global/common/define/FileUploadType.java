package com.example.demo.admin.global.common.define;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * TODO :: 재점검 필요
 */
@Getter
@RequiredArgsConstructor
public enum FileUploadType {
    CUSTOMER_NOTICE("C:/demo-admin/customer/notice/", 3, 1048576L, new String[]{"pdf", "xls", "xlsx", "jpg", "jpeg", "png"}),
    CUSTOMER_FAQ("C:/demo-admin/customer/faq/", Integer.MAX_VALUE, Long.MAX_VALUE, new String[]{"jpg", "jpeg", "png"})
    ;

    private final String fileDir;
    private final int maxCount;
    private final long maxSize;
    private final String[] extensions;

    public String getStorePath(String storeFileName) {
        return fileDir + storeFileName;
    }
}