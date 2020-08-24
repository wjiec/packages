package com.wjiec.tinder.springinaction.di;

import com.wjiec.tinder.springinaction.di.knight.Knight;
import com.wjiec.tinder.springinaction.di.quest.Quest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("di/knight-bean.xml");
        Quest quest = (Quest) context.getBean("quest");
        Knight knight = (Knight) context.getBean("knight");

        System.out.println(quest.getClass().getName());
        quest.embark();

        System.out.println(knight.getClass().getName());
        knight.embark();
    }

}
