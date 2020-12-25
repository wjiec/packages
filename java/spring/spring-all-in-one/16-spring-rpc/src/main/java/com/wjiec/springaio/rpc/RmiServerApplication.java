package com.wjiec.springaio.rpc;

import com.wjiec.springaio.rpc.service.EchoService;
import com.wjiec.springaio.rpc.service.EchoServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
@SuppressWarnings("deprecation")
public class RmiServerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RmiServerApplication.class);
        context.register(EchoServiceImpl.class);
        context.refresh();

        // do nothing
    }

    @Bean
    public RmiServiceExporter rmiServiceExporter(EchoService echoService) {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(echoService);
        rmiServiceExporter.setServiceName("EchoService");
        rmiServiceExporter.setServiceInterface(EchoService.class);
        rmiServiceExporter.setRegistryPort(1088);

        return rmiServiceExporter;
    }

}
