package com.wjiec.springaio.rest;

import com.wjiec.springaio.rest.config.ServletConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@ComponentScan
@EnableWebMvc
public class Application extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {Application.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
