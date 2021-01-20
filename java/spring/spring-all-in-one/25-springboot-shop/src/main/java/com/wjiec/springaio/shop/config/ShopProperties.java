package com.wjiec.springaio.shop.config;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "shop")
public class ShopProperties {

    private Server server = new Server();
    
    @Data
    public static class Server {
        private String listen = "UNKNOWN";
    }

    private Pagination pagination = new Pagination();

    @Data
    public static class Pagination {
        @Range(min = 5, max = 200, message = "size of pagination must ust be between {min} and {max}")
        private int size = 20;
    }

}
