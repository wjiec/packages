package com.wjiec.springaio.stomp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@ComponentScan
public class Application extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {Application.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {Application.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
