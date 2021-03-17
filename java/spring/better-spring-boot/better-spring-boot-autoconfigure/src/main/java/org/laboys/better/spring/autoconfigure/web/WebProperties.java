package org.laboys.better.spring.autoconfigure.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "barley.web")
public class WebProperties {

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

}
