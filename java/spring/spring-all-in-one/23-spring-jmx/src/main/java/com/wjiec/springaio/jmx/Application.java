package com.wjiec.springaio.jmx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import com.wjiec.springaio.jmx.service.PaginationService;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.RegistrationPolicy;

import javax.management.Notification;
import javax.management.NotificationListener;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        PaginationService paginationService = context.getBean(PaginationService.class);
        while (true) {
            System.out.println(paginationService.getPaginationSize());
            Thread.sleep(1000);
        }
    }

    @Bean
    public MBeanExporter mBeanExporter(PaginationService paginationService
        /*, MBeanServer mBeanServer */
        /*, MBeanInfoAssembler assembler*/) {
        MBeanExporter exporter = new MBeanExporter();

        Map<String, Object> beans = new HashMap<>();
        beans.put("spring:name=PaginationService", paginationService);
        exporter.setBeans(beans);
//        exporter.setServer(mBeanServer);
//        exporter.setAssembler(assembler);

        exporter.setRegistrationPolicy(RegistrationPolicy.IGNORE_EXISTING);

        Map<String, PaginationListener> mappings = new HashMap<>();
        mappings.put("spring:name=PaginationService", new PaginationListener());
        exporter.setNotificationListenerMappings(mappings);

        return exporter;
    }

//    @Bean
//    public MBeanServerFactoryBean mBeanServerFactoryBean() {
//        return new MBeanServerFactoryBean();
//    }

//    @Bean
//    public MethodNameBasedMBeanInfoAssembler methodNameBasedMBeanInfoAssembler() {
//        MethodNameBasedMBeanInfoAssembler assembler = new MethodNameBasedMBeanInfoAssembler();
//        assembler.setManagedMethods("getPaginationSize", "setPaginationSize");
//
//        return assembler;
//    }

//    @Bean
//    public InterfaceBasedMBeanInfoAssembler interfaceBasedMBeanInfoAssembler() {
//        InterfaceBasedMBeanInfoAssembler assembler = new InterfaceBasedMBeanInfoAssembler();
//        assembler.setManagedInterfaces(PaginationService.class);
//
//        return assembler;
//    }

    @Bean
    public ConnectorServerFactoryBean connectorServerFactoryBean() {
        ConnectorServerFactoryBean factoryBean = new ConnectorServerFactoryBean();
        factoryBean.setServiceUrl("service:jmx:rmi://localhost:9876");

        return factoryBean;
    }

    public static class PaginationListener implements NotificationListener {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            System.out.println(notification);
            System.out.println(handback);
        }
    }

}
