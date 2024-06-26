package com.example.demo.admin.domain.validation;

import com.example.demo.admin.domain.customer.define.NoticeType;
import com.example.demo.admin.domain.customer.dto.NoticeAddDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Slf4j
public class BeanValidationTest {

    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        NoticeAddDto noticeAddDto = new NoticeAddDto();
        noticeAddDto.setTitle(" ");
        noticeAddDto.setContents("test");
        noticeAddDto.setDisplayYn("Y");
        noticeAddDto.setNoticeType(NoticeType.NOTICE);

        Set<ConstraintViolation<NoticeAddDto>> violations = validator.validate(noticeAddDto);
        for (ConstraintViolation<NoticeAddDto> violation : violations) {
            log.info("violation = {}", violation);
            log.info("violation.getMessage = {}", violation.getMessage());

            Assertions.assertThat(violation.getMessage()).isEqualTo("제목을 입력해 주세요.");
        }
    }
}