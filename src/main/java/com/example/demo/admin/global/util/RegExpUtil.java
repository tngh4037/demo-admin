package com.example.demo.admin.global.util;

import com.example.demo.admin.global.common.define.PrivacyType;

public class RegExpUtil {

    /**
     * 개인정보 마스킹 처리
     *
     * @param type 유형
     * @param input 데이터
     * @return maskedPrivacy
     */
    public static String getMaskingPrivacy(PrivacyType type, String input) {
        return switch (type) {
            case ID -> getMaskedId(input);
            case EMAIL -> getMaskedEmail(input);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * 아이디 마스킹(앞2자리를 제외한 나머지)
     * Ex. ab*****
     *
     * @param id 아이디
     * @return maskedEmail
     */
    private static String getMaskedId(String id) {
        return id.replaceAll(RegExpPattern.ID_MASKING_PATTERN, "*");
    }

    /**
     * 이메일 마스킹(ID 중 앞2자리를 제외한 나머지)
     * Ex. ab*****@company.co.kr
     *
     * @param email 이메일
     * @return maskedEmail
     */
    private static String getMaskedEmail(String email) {
        return email.replaceAll(RegExpPattern.EMAIL_MASKING_PATTERN, "*");
    }

}
