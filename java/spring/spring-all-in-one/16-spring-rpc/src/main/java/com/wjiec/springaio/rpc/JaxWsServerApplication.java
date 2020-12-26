package com.wjiec.springaio.rpc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

@Configuration
public class JaxWsServerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(JaxWsServerApplication.class);
        context.refresh();
    }

    @Bean
    public SimpleJaxWsServiceExporter simpleJaxWsServiceExporter() {
        return new SimpleJaxWsServiceExporter();
    }

    // not working
    public static class EchoServiceEndpoint {
    }

}
