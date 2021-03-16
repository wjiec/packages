package org.laboys.better.spring.autoconfigure.validation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ValidationProperties.PREFIX)
public class ValidationProperties {

    /**
     * 验证器配置前缀
     */
    public static final String PREFIX = "better.validation";

    /**
     * 验证器消息配置
     */
    private Message message = new Message();

    /**
     * 验证器消息配置
     */
    @Data
    public static class Message {

        /**
         * 验证器类型名前缀
         */
        private String[] basename = new String[]{"validation/messages", "ValidationMessages"};

    }

}
