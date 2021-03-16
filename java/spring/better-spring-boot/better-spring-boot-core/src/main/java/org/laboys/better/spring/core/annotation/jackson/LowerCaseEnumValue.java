package org.laboys.better.spring.core.annotation.jackson;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.laboys.better.spring.core.jackson.serializer.LowerCaseEnumValueSerializer;

import java.lang.annotation.*;

/**
 * 以小写形式序列化或者反序列化枚举值
 */
@Documented
@JacksonAnnotationsInside
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = LowerCaseEnumValueSerializer.class)
public @interface LowerCaseEnumValue {}
