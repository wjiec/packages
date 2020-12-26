package com.wjiec.springaio.rpc;

import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.io.Hessian2Constants;
import com.wjiec.springaio.rpc.service.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

@SuppressWarnings("deprecation")
@Configuration
public class CauchoClientApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CauchoClientApplication.class);
        context.refresh();

        EchoService echoService = context.getBean(EchoService.class);
        System.out.println(echoService);
        System.out.println(echoService.repeated("hello", 10));
        System.out.println(echoService.max(10, -1));
    }

    @Bean
    public HessianProxyFactoryBean hessianProxyFactoryBean() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://localhost:8080/echo.hessian");
        factoryBean.setServiceInterface(EchoService.class);

        return factoryBean;
    }

}
