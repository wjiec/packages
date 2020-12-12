package com.wjiec.advanced.javawiring.shopping;

import com.wjiec.advanced.javawiring.annotation.Cold;
import com.wjiec.advanced.javawiring.annotation.Hot;
import com.wjiec.advanced.javawiring.person.Person;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
public class Food {

    @Bean
    @Primary
    @Conditional(AdultCondition.class)
    public Rice rice(@Cold Item dessert, @Qualifier("chopsticks") Item cutlery) {
        return new Rice(dessert, cutlery);
    }

    @ToString
    @AllArgsConstructor
    public static class Rice implements Item {
        private final Item dessert;
        private final Item cutlery;
    }

    @Bean
    @Primary
    @Conditional(ChildrenCondition.class)
    public Milk milk(@Hot Item dessert, @Qualifier("bottle") Item bottle) {
        return new Milk(dessert, bottle);
    }

    @ToString
    @AllArgsConstructor
    public static class Milk implements Item {
        private final Item dessert;
        private final Item cutlery;
    }

    @Bean
    public Vegetable vegetable() {
        return new Vegetable();
    }

    public static class Vegetable implements Item {}

    public static class AdultCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Person person = context.getBeanFactory().getBean(Person.class);
            return person.getAge() >= 18;
        }
    }

    public static class ChildrenCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Person person = context.getBeanFactory().getBean(Person.class);
            return person.getAge() < 18;
        }
    }

}
