package com.wjiec.tinder.springinaction.runtimevalueinject;

import com.wjiec.tinder.springinaction.runtimevalueinject.spel.Literal;
import org.springframework.context.annotation.*;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
@ComponentScan
@PropertySource("classpath:/runtimevalueinject/spel.properties")
public class SpELApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpELApplication.class);
        context.refresh();

        System.out.println(context.getBean(Literal.class));
    }

    @Bean
    public SpelExpressionParser spelExpressionParser() {
        SpelParserConfiguration configuration = new SpelParserConfiguration(true, true);
        return new SpelExpressionParser(configuration);
    }

}
