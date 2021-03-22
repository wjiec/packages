package org.laboys.better.spring.core.annotation.web;

import org.laboys.better.spring.core.web.decoration.StatusCode;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestException {

    /**
     * 异常对应使用的错误码
     */
    StatusCode value() default StatusCode.UNASSIGNED_ERROR;

}
