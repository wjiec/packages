package org.laboys.better.spring.validation.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator;
import org.laboys.better.spring.annotation.constraints.NullablePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 增强版Pattern验证器, 支持通过空值判断
 */
public class NullablePatternValidator implements ConstraintValidator<NullablePattern, String> {

    /**
     * 原始正则验证器实现
     */
    private final PatternValidator validator;

    /**
     * 是否允许空值直接通过验证
     */
    private boolean nullable = false;

    public NullablePatternValidator() {
        validator = new PatternValidator();
    }

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
