package com.wjiec.springaio.rpc;

import com.wjiec.springaio.rpc.service.EchoService;
import com.wjiec.springaio.rpc.service.EchoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Properties;

@SuppressWarnings("deprecation")
@Configuration
public class CauchoServerApplication extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {CauchoServerApplication.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {CauchoServerApplication.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/", "*.hessian", "*.burlap"};
    }

    @Bean
    public EchoService echoService() {
        return new EchoServiceImpl();
    }

    @Bean
    public HessianServiceExporter hessianExporter(EchoService echoService) {
        HessianServiceExporter serviceExporter = new HessianServiceExporter();
        serviceExporter.setService(echoService);
        serviceExporter.setServiceInterface(EchoService.class);

        return serviceExporter;
    }

    @Bean
    public HandlerMapping hessianHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties mapping = new Properties();
        mapping.setProperty("/echo.hessian", "hessianExporter");
        handlerMapping.setMappings(mapping);

        return handlerMapping;
    }

}
