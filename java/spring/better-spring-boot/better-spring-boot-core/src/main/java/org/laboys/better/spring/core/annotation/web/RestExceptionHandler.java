package org.laboys.better.spring.core.annotation.web;

import org.laboys.better.spring.core.web.decoration.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestExceptionHandler {

    /**
     * 需要处理的异常基类
     */
    Class<? extends Throwable> value();

    /**
     * 默认错误码
     */
    StatusCode statusCode() default StatusCode.UNASSIGNED_ERROR;

    /**
     * 对应的Http状态码
     */
    HttpStatus httpStatus() default HttpStatus.INTERNAL_SERVER_ERROR;

}
