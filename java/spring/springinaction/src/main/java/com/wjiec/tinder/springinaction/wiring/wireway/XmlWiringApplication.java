package com.wjiec.tinder.springinaction.wiring.wireway;

import com.wjiec.tinder.springinaction.wiring.model.admin.Admin;
import com.wjiec.tinder.springinaction.wiring.model.admin.SuperMan;
import com.wjiec.tinder.springinaction.wiring.model.draw.Palette;
import com.wjiec.tinder.springinaction.wiring.model.food.Food;
import com.wjiec.tinder.springinaction.wiring.model.food.Meat;
import com.wjiec.tinder.springinaction.wiring.model.media.CompactDisc;
import com.wjiec.tinder.springinaction.wiring.model.media.SgtPeppers;
import com.wjiec.tinder.springinaction.wiring.model.player.CDPlayer;
import com.wjiec.tinder.springinaction.wiring.model.player.MediaPlayer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlWiringApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("/wiring/xml-wiring.xml");
        context.refresh();

        System.out.println(context.getBean("cd"));
        System.out.println(context.getBean(CompactDisc.class));
        System.out.println(context.getBean(SgtPeppers.class));
        System.out.println(context.getBean(MediaPlayer.class));
        System.out.println(context.getBean(CDPlayer.class));
        System.out.println(context.getBean(SuperMan.class));
        context.getBean(SuperMan.class).dance();

        System.out.println(context.getBean(Meat.class));
        System.out.println(context.getBean(Food.class));

        System.out.println(context.getBean(Palette.class));
        System.out.println(context.getBean(Palette.class).getColors());
        System.out.println(context.getBean(Palette.class).getPrimaryColors());
    }

}
