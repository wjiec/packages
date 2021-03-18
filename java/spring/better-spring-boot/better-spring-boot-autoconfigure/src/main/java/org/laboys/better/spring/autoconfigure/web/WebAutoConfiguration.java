package org.laboys.better.spring.autoconfigure.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebProperties.class)
@Import({WebExceptionAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class WebAutoConfiguration {}
