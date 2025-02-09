package com.example.demo.admin.domain.customer.validator;

import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

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
            errors.reject("customer.notice.invalid.search.displayYn");
        }

        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");
    }
}
