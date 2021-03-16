package org.laboys.better.spring.autoconfigure.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.laboys.better.spring.core.jackson.deserializer.MoneyDeserializer;
import org.laboys.better.spring.core.jackson.serializer.MoneySerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(ObjectMapper.class)
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class Jackson2AutoConfiguration {

    /**
     * 启用的Jackson功能
     */
    private final static Object[] enabledFeatures = new Object[]{
        MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS,
        DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,
        DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
    };

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.featuresToEnable(enabledFeatures);
            // money serializer/deserializer
            jacksonObjectMapperBuilder.serializers(new MoneySerializer());
            jacksonObjectMapperBuilder.deserializers(new MoneyDeserializer());
        };
    }

}
