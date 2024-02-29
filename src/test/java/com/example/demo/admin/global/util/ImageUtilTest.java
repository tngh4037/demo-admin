package com.example.demo.admin.global.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ImageUtilTest {

    @Test
    @DisplayName("img 태그의 src 속성 값을 조회한다.")
    void getSrcPath() {
        // given
        String content = "<!DOCTYPE html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Insert title here</title>" +
                "</head>" +
                "<h1>img html</h1>" +
                "<img src='/test/path/images/one.jpg'/>" +
                "<img src='/test/path/images/two.png'/>" +
                "<img src='/test/path/images/three.jpeg'/>";

        // when
        List<String> srcPath = ImageUtil.getSrcPath(content);

        // then
        Assertions.assertThat(srcPath.size()).isEqualTo(3);
    }
}