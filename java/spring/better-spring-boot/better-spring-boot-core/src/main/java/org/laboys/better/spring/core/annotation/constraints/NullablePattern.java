package org.laboys.better.spring.core.annotation.constraints;

import org.laboys.better.spring.core.validator.NullablePatternValidator;
import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 增强Pattern验证器注解, 支持nullable参数
 */
@Documented
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE})
@Constraint(validatedBy = NullablePatternValidator.class)
public @interface NullablePattern {

    /**
     * 默认正则验证器实现
     */
    Pattern value();

    /**
     * 空值是否可以直接通过验证
     */
    boolean nullable() default false;

    /**
     * 错误消息模板
     */
    @AliasFor(attribute = "message", annotation = Pattern.class)
    String message() default "{javax.validation.constraints.Pattern.message}";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};

}
