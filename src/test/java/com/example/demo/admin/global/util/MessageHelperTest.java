package com.example.demo.admin.global.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles(profiles = "local")
class MessageHelperTest {

    @Autowired
    MessageSource ms;

    @Test
    void getMessage() {
        String result = ms.getMessage("typeMismatch", null, null);
        assertThat(result).isEqualTo("잘못된 요청입니다.");
    }

    @Test
    void notFoundMessageCode_throw_exception() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCode_default_message() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {
        String message = ms.getMessage("login.invalid.adminStatus", new Object[]{"중지"}, null);
        assertThat(message).isEqualTo("중지 처리된 계정입니다. 마스터 관리자에 문의해 주세요.");
    }
}