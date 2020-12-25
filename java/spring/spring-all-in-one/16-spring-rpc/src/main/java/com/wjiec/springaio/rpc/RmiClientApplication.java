package com.wjiec.springaio.rpc;

import com.wjiec.springaio.rpc.service.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
@SuppressWarnings("deprecation")
public class RmiClientApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RmiClientApplication.class);
        context.refresh();

        EchoService echoService = context.getBean(EchoService.class);
        System.out.println(echoService);
        System.out.println(echoService.repeated("hello", 10));
        System.out.println(echoService.max(10, -1));
    }

    @Bean
    public RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://localhost:1088/EchoService");
        factoryBean.setServiceInterface(EchoService.class);

        return factoryBean;
    }

}
