package com.github.wjiec.human;

import com.github.wjiec.stream.AliceInWonderLand;
import com.github.wjiec.stream.Splitter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Person {

    private int id;

    private int age;

    private String name;

    private static AtomicInteger ai;

    static {
        ai = new AtomicInteger();
    }

    {
        id = ai.addAndGet(1);
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public static Stream<Person> random() {
        return Stream.generate(() -> new Person(Splitter.split(AliceInWonderLand.ReadAsStream())
            .filter(s -> s.length() != 0)
            .findAny().orElse("unknown"), (int)(Math.random() * 100)));
    }

}
