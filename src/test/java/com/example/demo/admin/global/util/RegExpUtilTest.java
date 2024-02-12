package com.example.demo.admin.global.util;

import com.example.demo.admin.global.common.define.PrivacyType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegExpUtilTest {

    String[] ids;
    String[] maskedIds;

    String[] emails;
    String[] maskedEmails;

    @BeforeEach
    void beforeEach() {
        ids = new String[]{"a", "ab", "abc", "abcd", "abcde"};
        maskedIds = new String[]{"a", "ab", "ab*", "ab**", "ab***"};

        emails = new String[]{"a@company.co.kr", "ab@company.co.kr", "abc@company.co.kr", "abcd@company.co.kr", "abcde@company.co.kr"};
        maskedEmails = new String[]{"a@company.co.kr", "ab@company.co.kr", "ab*@company.co.kr", "ab**@company.co.kr", "ab***@company.co.kr"};
    }

    @Test
    @DisplayName("아이디 - 앞2자리를 제외한 나머지는 *로 처리한다.")
    void test_id() {
        // given & when & then
        for (int i = 0; i < ids.length; i++) {
            Assertions.assertThat(RegExpUtil.getMaskingPrivacy(PrivacyType.ID, ids[i])).isEqualTo(maskedIds[i]);
        }
    }

    @Test
    @DisplayName("이메일 - 이메일ID 중 앞2자리를 제외한 나머지는 *로 처리한다.")
    void test_email() {
        // given & when & then
        for (int i = 0; i < emails.length; i++) {
            Assertions.assertThat(RegExpUtil.getMaskingPrivacy(PrivacyType.EMAIL, emails[i])).isEqualTo(maskedEmails[i]);
        }
    }
}