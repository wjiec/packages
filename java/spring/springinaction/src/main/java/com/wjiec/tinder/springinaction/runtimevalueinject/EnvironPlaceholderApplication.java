package com.wjiec.tinder.springinaction.runtimevalueinject;

import com.wjiec.tinder.springinaction.runtimevalueinject.envplaceholder.Range;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:/runtimevalueinject/range.properties")
public class EnvironPlaceholderApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(EnvironPlaceholderApplication.class);
        context.refresh();

        System.out.println(context.getEnvironment().getProperty("range.start"));
        System.out.println(context.getEnvironment().getProperty("range.end",Integer.class));
        System.out.println(context.getEnvironment().getProperty("range.alert"));
        System.out.println(context.getEnvironment().getProperty("range.notify", "notify"));

        System.out.println(context.getBean(Range.class));
    }

}
