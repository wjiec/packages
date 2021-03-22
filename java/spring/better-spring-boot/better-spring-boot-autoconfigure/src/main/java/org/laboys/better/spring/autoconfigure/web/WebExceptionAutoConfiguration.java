package org.laboys.better.spring.autoconfigure.web;

import org.laboys.better.spring.core.deploy.DeployEnvironment;
import org.laboys.better.spring.core.web.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
class WebExceptionAutoConfiguration {

    @Bean
    @Profile({DeployEnvironment.NOT_PRODUCTION, DeployEnvironment.NOT_CANARY_PRODUCTION})
    public DevelopmentExceptionHandler developmentExceptionHandler() {
        return new DevelopmentExceptionHandler();
    }

    @Bean
    @Profile({DeployEnvironment.PRODUCTION, DeployEnvironment.CANARY_PRODUCTION})
    public ProductionExceptionHandler productionExceptionHandler() {
        return new ProductionExceptionHandler();
    }

    @Bean
    public BetterExceptionHandler betterExceptionHandler() {
        return new BetterExceptionHandler();
    }

    @Bean
    public MethodUnsupportedHandler methodUnsupportedHandler() {
        return new MethodUnsupportedHandler();
    }

    @Bean
    public ValidationBindErrorHandler validationBindErrorHandler() {
        return new ValidationBindErrorHandler();
    }


}
