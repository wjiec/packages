package org.laboys.better.spring.core.convert;

import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.laboys.better.spring.core.annotation.convert.EnumDefaultValue;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

@Data
@Builder
@AllArgsConstructor
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    /**
     * 是否将无效的枚举值替换返回为默认枚举值
     */
    private final boolean readUnknownEnumUsingDefaultValue;

    /**
     * 是否支持进行大小写不敏感的枚举值匹配
     */
    private final boolean acceptCaseInsensitiveEnums;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> target) {
        return new StringToEnumConverter(target);
    }

    @AllArgsConstructor
    private class StringToEnumConverter<E extends Enum<E>> implements Converter<String, E> {
        private final Class<E> target;

        @Override
        public E convert(String source) {
            E defaultValue = null;
            Function<Field, E> toEnum = (f) -> Enums.getIfPresent(target, f.getName()).orNull();
            for (var field : target.getDeclaredFields()) {
                if (field.getName().equals(source)) {
                    return toEnum.apply(field);
                }

                if (acceptCaseInsensitiveEnums && field.getName().equalsIgnoreCase(source)) {
                    return toEnum.apply(field);
                }

                if (readUnknownEnumUsingDefaultValue && defaultValue == null) {
                    Annotation annotation = field.getAnnotation(EnumDefaultValue.class);
                    if (annotation != null) {
                        defaultValue = toEnum.apply(field);
                    }
                }
            }

            return defaultValue;
        }
    }

}
