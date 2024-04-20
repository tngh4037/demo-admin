package com.example.demo.admin.global.converter;

import com.example.demo.admin.domain.customer.define.FaqType;
import com.example.demo.admin.global.config.converter.StringToEnumConverterFactory;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverterFactory(new StringToEnumConverterFactory());

        assertThat(conversionService.convert("EV", FaqType.class)).isEqualTo(FaqType.EVENT);
    }
}