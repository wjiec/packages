package org.laboys.better.spring.core.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator;
import org.laboys.better.spring.core.annotation.constraints.NullablePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 增强版Pattern验证器, 支持通过空值判断
 */
public class NullablePatternValidator implements ConstraintValidator<NullablePattern, String> {

    /**
     * 原始正则验证器实现
     */
    private final PatternValidator validator = new PatternValidator();

    /**
     * 是否允许空值直接通过验证
     */
    private boolean nullable = false;

    @Override
    public void initialize(NullablePattern parameter) {
        nullable = parameter.nullable();
        validator.initialize(parameter.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable && (value == null || value.isBlank())) {
            return true;
        }

        return validator.isValid(value, context);
    }

}
