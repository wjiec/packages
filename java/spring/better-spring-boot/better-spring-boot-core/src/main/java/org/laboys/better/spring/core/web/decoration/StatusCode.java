package org.laboys.better.spring.core.web.decoration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {

    /**
     * 成功
     */
    SUCCESS(0),

    /**
     * 参数错误
     */
    PARAMS_ERROR(4000),

    /**
     * 缺少必须参数
     */
    PARAMS_MISSING(4001),

    /**
     * 请求方法错误
     */
    METHOD_ERROR(4500),

    /**
     * 因为权限问题访问被拒绝
     */
    ACCESS_DENY(4300),

    /**
     * 查找的资源不存在
     */
    RESOURCE_NOT_FOUND(4400),

    /**
     * 服务内部错误
     */
    INTERNAL_ERROR(5000),

    /**
     * 未指派具体类型的错误
     */
    UNASSIGNED_ERROR(-1),

    /**
     * 致命错误
     */
    FATAL_ERROR(-127);

    /**
     * 错误码
     */
    @JsonValue
    private final int code;

}
