package com.wjiec.tinder.springinaction.wiring.wireway;

import com.wjiec.tinder.springinaction.wiring.model.food.Food;
import com.wjiec.tinder.springinaction.wiring.model.food.FoodConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MixinWiringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(FoodConfig.class);
        context.refresh();

        System.out.println(context.getBean(Food.class));
    }

}
