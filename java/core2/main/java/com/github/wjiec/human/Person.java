package com.github.wjiec.human;

import com.github.wjiec.stream.AliceInWonderLand;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Person implements Human {

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

    @Override
    public String toString() {
        return new StringJoiner(" - ")
            .add(String.valueOf(getId()))
            .add(getName())
            .add(String.valueOf(getAge()))
            .toString();
    }

    public static Stream<Person> random() {
        return AliceInWonderLand.words(s -> s.length() != 0).map((n) -> new Person(n, (int)(Math.random() * 100)));
    }

}
