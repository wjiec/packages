package com.wjiec.springaio.xmlwiring;

import com.wjiec.springaio.xmlwiring.model.Author;
import com.wjiec.springaio.xmlwiring.model.Music;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
        context.refresh();

        System.out.println(context.getBean("author"));
        System.out.println(context.getBean(Author.class));

        System.out.println(context.getBean("music"));
        System.out.println(context.getBean(Music.class));
    }

}
