package com.example.demo.admin.global.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CommonUtilTest {

    @Test
    void isEmpty() {
        // given
        String str1 = null;
        String str2 = " ";
        String str3 = "";
        String str4 = " a ";
        List<String> list = new ArrayList<>();

        // when
        boolean str1_result = CommonUtil.isEmpty(str1);
        boolean str2_result = CommonUtil.isEmpty(str2);
        boolean str3_result = CommonUtil.isEmpty(str3);
        boolean str4_result = CommonUtil.isEmpty(str4);
        boolean list_result = CommonUtil.isEmpty(list);

        // then
        Assertions.assertThat(str1_result).isTrue();
        Assertions.assertThat(str2_result).isTrue();
        Assertions.assertThat(str3_result).isTrue();
        Assertions.assertThat(str4_result).isFalse();
        Assertions.assertThat(list_result).isTrue();
    }

}