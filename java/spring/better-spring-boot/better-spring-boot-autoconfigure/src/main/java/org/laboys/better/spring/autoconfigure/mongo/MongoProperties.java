package org.laboys.better.spring.autoconfigure.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = MongoProperties.PREFIX)
public class MongoProperties {

    /**
     * Mongo配置前缀
     */
    public static final String PREFIX = "barley.mongo";

    /**
     * 是否删除默认添加的_class字段映射
     */
    private Boolean removeClassMapping = true;

}
