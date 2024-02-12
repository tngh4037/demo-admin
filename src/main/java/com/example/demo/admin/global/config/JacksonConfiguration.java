package com.example.demo.admin.global.config;

import com.example.demo.admin.global.config.define.EnumMapperType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
public class JacksonConfiguration {

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // 1) enum 처리
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<EnumMapperType> modifyEnumDeserializer(DeserializationConfig config,
                                                                     final JavaType type,
                                                                     BeanDescription beanDesc,
                                                                     final JsonDeserializer<?> deserializer) {
                return new JsonDeserializer<EnumMapperType>() {
                    @Override
                    public EnumMapperType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                        Class<? extends EnumMapperType> rawClass = (Class<? extends EnumMapperType>) type.getRawClass();
                        for (EnumMapperType e : rawClass.getEnumConstants()) {
                            if (jp.getValueAsString().equals(e.getCode()) ||
                                    jp.getValueAsString().equals(((Enum<?>) e).name())) {
                                return e;
                            }
                        }
                        throw new IllegalArgumentException("No enum constant");
                    }
                };
            }
        });
        module.addSerializer(EnumMapperType.class, new StdSerializer<EnumMapperType>(EnumMapperType.class) {
            @Override
            public void serialize(EnumMapperType value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeStartObject();
                jgen.writeStringField("code", value.getCode());
                jgen.writeStringField("title", value.getTitle());
                jgen.writeEndObject();
            }
        });

        // 2) 날짜 처리
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul")));
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        objectMapper.registerModules(javaTimeModule, module);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 3) (Json -> 객체 매핑시) 객체 내부에 없는 field 정보가 JSON 데이터에 존재하는 경우 무시한다.
        //  ㄴ 참고1) 해당 클래스 레벨에만 @JsonIgnoreProperties(ignoreUnknown = true) 적용해도 무관: 해당 클래스에 선언한 필드 이외 모든 요소를 제외하겠다.
        //  ㄴ 참고2) 해당 클래스 레벨에만 @JsonIgnoreProperties({"name"}})  적용해도 무관: 특정 요소만 제외하겠다.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }
}