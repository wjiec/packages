package org.laboys.better.spring.autoconfigure.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = WebProperties.PREFIX)
public class WebProperties {

    /**
     * Web配置前缀
     */
    public static final String PREFIX = "barley.web";

    /**
     * 支持开启的相关功能
     */
    private Feature feature = new Feature();

    /**
     * 增强功能
     */
    @Data
    public static class Feature {
        /**
         * 是否支持大小写不敏感的枚举值
         */
        private Boolean acceptCaseInsensitiveEnums = true;

        /**
         * 是否支持使用默认值替代未知的枚举值
         */
        private Boolean readUnknownEnumUsingDefaultValue = true;
    }

    /**
     * 接口装饰包装
     */
    @Data
    public static class Decoration {
        /**
         * 是否开启接口装饰
         */
        private Boolean enabled = true;

        /**
         * 在成功时是否开启
         */
        private Boolean onSuccess = true;

        /**
         * 在失败时时候开启
         */
        private Boolean onFailure = true;
    }

}
