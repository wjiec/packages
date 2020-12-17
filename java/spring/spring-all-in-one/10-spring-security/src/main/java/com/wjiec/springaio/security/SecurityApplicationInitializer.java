package com.wjiec.springaio.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan
@EnableWebMvc
@PropertySource("/application.yml")
public class SecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityApplicationInitializer() {
        super(SecurityApplicationInitializer.class);
    }

}
