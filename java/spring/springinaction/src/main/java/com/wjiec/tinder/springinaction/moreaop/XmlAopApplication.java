package com.wjiec.tinder.springinaction.moreaop;

import com.wjiec.tinder.springinaction.moreaop.encore.Encoreable;
import com.wjiec.tinder.springinaction.moreaop.performance.Performance;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlAopApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("classpath:/moreaop/performance-aop.xml");
        context.refresh();

        System.out.println();
        Performance concert = (Performance) context.getBean("concert");
        concert.perform();

        System.out.println();
        Performance movie = (Performance) context.getBean("movie");
        movie.perform();

        System.out.println();
        Encoreable encoreConcert = (Encoreable) context.getBean("concert");
        encoreConcert.performEncore("concert", 10);

        System.out.println();
        Encoreable encoreMovie = (Encoreable) context.getBean("movie");
        encoreMovie.performEncore("movie", 20);
    }

}
