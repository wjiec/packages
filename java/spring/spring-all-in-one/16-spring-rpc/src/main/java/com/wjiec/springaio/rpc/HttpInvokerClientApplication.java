package com.wjiec.springaio.rpc;

import com.wjiec.springaio.rpc.service.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@SuppressWarnings("deprecation")
@Configuration
public class HttpInvokerClientApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(HttpInvokerClientApplication.class);
        context.refresh();

        EchoService echoService = context.getBean(EchoService.class);
        System.out.println(echoService);
        System.out.println(echoService.repeated("hello", 10));
        System.out.println(echoService.max(10, -1));
    }

    @Bean
    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean() {
        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceUrl("http://localhost:8080/echo.invoker");
        factoryBean.setServiceInterface(EchoService.class);

        return factoryBean;
    }

}
