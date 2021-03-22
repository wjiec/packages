package org.laboys.better.spring.autoconfigure.web;

import org.laboys.better.spring.core.web.RestExceptionAdvice;
import org.laboys.better.spring.core.web.RestResultAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebProperties.class)
@Import({WebExceptionAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class WebAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "decoration.enabled", havingValue = "true", matchIfMissing = true)
    public static class DecorationConfiguration {
        @Bean
        @ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "decoration.on-success", havingValue = "true", matchIfMissing = true)
        public RestResultAdvice restResultAdvice(ApplicationContext context) {
            return new RestResultAdvice(context);
        }

        @Bean
        @ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "decoration.on-failure", havingValue = "true", matchIfMissing = true)
        public RestExceptionAdvice restExceptionAdvice(ApplicationContext context) {
            return new RestExceptionAdvice(context);
        }
    }

}
