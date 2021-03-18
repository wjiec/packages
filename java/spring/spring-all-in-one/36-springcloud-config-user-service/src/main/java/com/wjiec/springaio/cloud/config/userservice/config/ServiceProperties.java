package com.wjiec.springaio.cloud.config.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(value = "service")
public class ServiceProperties {

    private Hello hello = new Hello();

    @Data
    public static class Hello {
        private String world;
        private String spring;
    }

    private World world = new World();

    @Data
    public static class World {
        private String vault;
    }

}
