package com.wjiec.tinder.springinaction.spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.wjiec.tinder.springinaction.spittr", excludeFilters = {
    @ComponentScan.Filter(value = EnableWebMvc.class, type = FilterType.ANNOTATION)
})
public class RootConfig {
}
