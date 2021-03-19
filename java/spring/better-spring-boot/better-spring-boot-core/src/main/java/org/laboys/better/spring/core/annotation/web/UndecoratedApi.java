package org.laboys.better.spring.core.annotation.web;

import java.lang.annotation.*;

/**
 * 标识一个接口不需要进行统一接口包装
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UndecoratedApi {}

