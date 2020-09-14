package com.wjiec.tinder.springinaction.moreaop;

import com.wjiec.tinder.springinaction.moreaop.encore.Encoreable;
import com.wjiec.tinder.springinaction.moreaop.performance.Concert;
import com.wjiec.tinder.springinaction.moreaop.performance.Movie;
import com.wjiec.tinder.springinaction.moreaop.performance.Performance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AnnotationAopApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationAopApplication.class);
        context.refresh();

        for (var name : context.getBeanDefinitionNames()) {
            System.out.printf("%s\n\t%s\n", name, context.getBean(name).getClass().getName());
        }

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
