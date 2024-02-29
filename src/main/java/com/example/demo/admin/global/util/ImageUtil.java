package com.example.demo.admin.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtil {

    private static final String IMG_SRC_PATTERN = "<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"; //img 태그 src 추출 정규 표현식

    public static List<String> getSrcPath(String content) {
        Pattern pattern = Pattern.compile(IMG_SRC_PATTERN);
        Matcher matcher = pattern.matcher(content);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }
}
