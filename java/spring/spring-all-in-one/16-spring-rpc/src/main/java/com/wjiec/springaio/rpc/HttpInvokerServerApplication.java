package com.wjiec.springaio.rpc;

import com.wjiec.springaio.rpc.service.EchoService;
import com.wjiec.springaio.rpc.service.EchoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Properties;

@SuppressWarnings("deprecation")
@Configuration
public class HttpInvokerServerApplication extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {HttpInvokerServerApplication.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {HttpInvokerServerApplication.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/", "*.invoker"};
    }

    @Bean
    public EchoService echoService() {
        return new EchoServiceImpl();
    }

    @Bean
    public HttpInvokerServiceExporter httpInvokerServiceExporter() {
        HttpInvokerServiceExporter serviceExporter = new HttpInvokerServiceExporter();
        serviceExporter.setService(echoService());
        serviceExporter.setServiceInterface(EchoService.class);

        return serviceExporter;
    }

    @Bean
    public HandlerMapping echoHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties properties = new Properties();
        properties.setProperty("/echo.invoker", "httpInvokerServiceExporter");

        handlerMapping.setMappings(properties);
        return handlerMapping;
    }

}
