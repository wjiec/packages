package com.wjiec.springaio.javaaop;

import com.wjiec.springaio.javaaop.database.Database;
import com.wjiec.springaio.javaaop.database.closer.Closer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        System.out.println();
        for (var name : context.getBeanDefinitionNames()) {
            System.out.println(name + " : " + context.getBean(name).getClass().getName() + " -> " + context.getBean(name));
        }
        System.out.println();

        Database mysql = (Database) context.getBean("mysql");
        System.out.println("mysql result: " + mysql.execute("select * from user;"));
        System.out.println();
        System.out.println("mysql result: " + mysql.execute("select * from role where role=?;", "hello"));
        if (mysql instanceof Closer) {
            ((Closer) mysql).close();
        }

        System.out.println("\n+++++\n");

        Database pgsql = (Database) context.getBean("postgresql");
        System.out.println("pgsql result: " + pgsql.execute("select * from user;"));
        System.out.println();
        System.out.println("pgsql result: " + pgsql.execute("select * from role where role=?;", "hello"));
        if (pgsql instanceof Closer) {
            ((Closer) pgsql).close();
        }
    }

}
