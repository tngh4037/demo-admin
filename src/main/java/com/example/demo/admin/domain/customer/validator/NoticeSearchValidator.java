package com.example.demo.admin.domain.customer.validator;

import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NoticeSearchValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NoticeSearchDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NoticeSearchDto searchDto = (NoticeSearchDto) target;
        if (!Strings.isEmpty(searchDto.getDisplayYn()) &&
                !searchDto.getDisplayYn().equals("Y") && !searchDto.getDisplayYn().equals("N")) {
            errors.reject("ERROR1", "노출 여부를 다시 확인해 주세요."); // TODO :: 에러 (코드 / 메시지) 관리될 수 있도록 개선할 것
        }
    }
}
