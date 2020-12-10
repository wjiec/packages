package com.wjiec.springaio.autowiring;

import com.wjiec.springaio.autowiring.worker.Programmer;
import com.wjiec.springaio.autowiring.worker.Treasurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("/application.properties")
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        Programmer programmer = context.getBean(Programmer.class);
        programmer.work();

        Treasurer treasurer = context.getBean(Treasurer.class);
        treasurer.work();
    }

}
