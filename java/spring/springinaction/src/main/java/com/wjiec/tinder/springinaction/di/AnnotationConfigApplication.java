package com.wjiec.tinder.springinaction.di;

import com.wjiec.tinder.springinaction.di.knight.Knight;
import com.wjiec.tinder.springinaction.di.quest.Quest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanConfig.class);
        context.refresh();

        Quest quest = (Quest) context.getBean("quest");
        System.out.println(quest.getClass().getName());
        quest.embark();

        Knight knight = context.getBean(Knight.class);
        System.out.println(knight.getClass().getName());
        knight.embark();
    }

}
