package com.wjiec.springaio.websocket;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@ComponentScan
public class WebsocketApplication extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {WebsocketApplication.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebsocketApplication.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
