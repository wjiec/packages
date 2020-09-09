package com.wjiec.tinder.springinaction.runtimevalueinject;

import com.wjiec.tinder.springinaction.runtimevalueinject.spel.Literal;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(lazyInit = true)
@PropertySource("classpath:/runtimevalueinject/spel.properties")
public class SpELApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpELApplication.class);
        context.refresh();

        for (var s : context.getEnvironment().getPropertySources()) {
            System.out.println(s);
        }

        System.out.println(context.getBean(Literal.class));
    }

}
