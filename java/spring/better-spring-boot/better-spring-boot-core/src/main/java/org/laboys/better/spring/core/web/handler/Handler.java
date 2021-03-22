package org.laboys.better.spring.core.web.handler;

import java.util.List;

/**
 * 异常处理器
 */
@FunctionalInterface
public interface Handler<T extends Throwable> {

    /**
     * 构建错误列表
     */
    List<?> errors(T ex);

}
