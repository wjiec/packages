package org.laboys.better.spring.core.support;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.laboys.better.spring.core.annotation.convert.EnumDefaultValue;
import org.laboys.better.spring.core.annotation.jackson.LowerCaseEnumValue;

/**
 * 支持多种Boolean字符串值, 并且支持空类型
 */
@LowerCaseEnumValue
@AllArgsConstructor
public enum NullableBoolean {

    /**
     * 逻辑真
     */
    TRUE(Boolean.TRUE), YES(Boolean.TRUE), ON(Boolean.TRUE), OK(Boolean.TRUE),

    /**
     * 逻辑假
     */
    FALSE(Boolean.FALSE), NO(Boolean.FALSE), OFF(Boolean.FALSE), BAD(Boolean.FALSE),

    /**
     * 逻辑空
     */
    EMPTY(null), NULL(null), @EnumDefaultValue @JsonEnumDefaultValue NONE(null);

    @Getter
    private final Boolean value;

}
