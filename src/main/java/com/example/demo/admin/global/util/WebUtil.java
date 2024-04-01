package com.example.demo.admin.global.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class WebUtil {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader(AJAX_HEADER_NAME);
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        return AJAX_HEADER_VALUE.equals(header) || MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader);
    }
}
