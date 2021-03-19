package org.laboys.better.spring.core.deploy;

import java.io.Serializable;

public class DeployEnvironment implements Serializable {
    private static final long serialVersionUID = 3111655472432313628L;

    /**
     * 开发环境, 开发人员使用
     */
    public static final String DEVELOPMENT = "dev";

    /**
     * 测试环境下的功能验收阶段, 测试人员使用
     */
    public static final String FEATURE_ACCEPTANCE_TEST = "fat";

    /**
     * 生产环境下的用户验收阶段, 测试人员使用
     */
    public static final String USER_ACCEPTANCE_TEST = "uat";

    /**
     * 线上预发阶段
     */
    public static final String CANARY_PRODUCTION = "canary";

    /**
     * 线上正式环境
     */
    public static final String PRODUCTION = "prod";

    /**
     * 开发环境, 开发人员使用
     */
    public static final String NOT_DEVELOPMENT = "!dev";

    /**
     * 测试环境下的功能验收阶段, 测试人员使用
     */
    public static final String NOT_FEATURE_ACCEPTANCE_TEST = "!fat";

    /**
     * 生产环境下的用户验收阶段, 测试人员使用
     */
    public static final String NOT_USER_ACCEPTANCE_TEST = "!uat";

    /**
     * 线上预发阶段
     */
    public static final String NOT_CANARY_PRODUCTION = "!canary";

    /**
     * 线上正式环境
     */
    public static final String NOT_PRODUCTION = "!prod";

}
