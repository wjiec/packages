package com.wjiec.springaio.webflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

@Configuration
@ImportResource("classpath:/beans.xml")
public class ServletConfig {

    @Bean
    public FlowHandlerMapping flowHandlerMapping(FlowDefinitionRegistry registry) {
        FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
        flowHandlerMapping.setFlowRegistry(registry);
        flowHandlerMapping.setInterceptors();

        return flowHandlerMapping;
    }

}
