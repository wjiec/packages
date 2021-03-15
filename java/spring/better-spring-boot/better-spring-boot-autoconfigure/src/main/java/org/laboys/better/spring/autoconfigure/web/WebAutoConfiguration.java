package org.laboys.better.spring.autoconfigure.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.stream.Collectors;

@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class WebAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    public static class WebMvcConfigurationAdapter implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                .allowedHeaders(Arrays.stream(HttpMethod.values())
                    .map(Enum::name).collect(Collectors.toList())
                    .toArray(new String[]{}));
        }

    }

}
