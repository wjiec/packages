package com.wjiec.tinder.springinaction.ambiguousbean;

import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Fluffy;
import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Soft;
import com.wjiec.tinder.springinaction.ambiguousbean.dessert.Dessert;
import com.wjiec.tinder.springinaction.ambiguousbean.fruit.Fruit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan
@Slf4j
public class AmbiguousBeanWiringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(AmbiguousBeanWiringApplication.class);
        context.refresh();

        context.getBean(QualifierWiring.class);
        context.getBean(PrimaryWiring.class);
        context.getBean(CustomAnnotationWiring.class);
    }

    @Component
    private static class QualifierWiring {
        @Autowired
        @Qualifier("apple")
        public void setFruit(Fruit fruit) {
            log.info(fruit.toString());
        }
    }

    @Component
    private static class PrimaryWiring {
        @Autowired
        public void setDessert(Dessert dessert) {
            log.info(dessert.toString()); // icrCream is mark primary
        }
    }

    @Component
    private static class CustomAnnotationWiring {
        @Autowired
        @Fluffy
        public void setDessert(Dessert dessert) {
            log.info(dessert.toString());
        }
    }

}
