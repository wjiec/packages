package com.wjiec.tinder.springinaction.aop;

import com.wjiec.tinder.springinaction.di.knight.Knight;
import com.wjiec.tinder.springinaction.di.quest.Quest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmbarkAopApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "di/knight-bean.xml", "aop/knight-aop.xml");

        Quest quest = context.getBean(Quest.class);
        System.out.println(quest);
        quest.embark();

        System.out.println();

        Knight knight = context.getBean(Knight.class);
        System.out.println(knight);
        knight.embark();
    }

}
