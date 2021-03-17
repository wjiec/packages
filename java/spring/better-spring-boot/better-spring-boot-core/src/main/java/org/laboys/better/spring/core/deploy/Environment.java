package org.laboys.better.spring.core.deploy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部署环境名称
 */
@AllArgsConstructor
public enum Environment {

    /**
     * 开发环境, 开发人员使用
     */
    DEVELOPMENT("dev"),

    /**
     * 测试环境下的功能验收阶段, 测试人员使用
     */
    FEATURE_ACCEPTANCE_TEST("fat"),

    /**
     * 生产环境下的功能验收阶段, 测试人员使用
     */
    USER_ACCEPTANCE_TEST("uat"),

    /**
     * 线上预发阶段
     */
    CANARY_PRODUCTION("canary"),

    /**
     * 线上正式环境
     */
    PRODUCTION("prod");

    /**
     * 环境名称简写
     */
    @Getter
    private final String value;

}
