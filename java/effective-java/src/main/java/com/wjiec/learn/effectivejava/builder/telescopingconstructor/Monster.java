package com.wjiec.learn.effectivejava.builder.telescopingconstructor;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.concurrent.atomic.AtomicInteger;

public class Monster {

    static final AtomicInteger ai = new AtomicInteger(0);

    private final long serial;
    private final String name;
    private final int age;
    private final double height;
    private final double weight;

    public Monster(String name) {
        this(name, (int)(Math.random() * 100), 100, 1000);
    }

    public Monster(String name, int age) {
        this(name, age, 100, 1000);
    }

    public Monster(String name, int age, int height) {
        this(name, age, height, 1000);
    }

    public Monster(String name, int age, int height, int weight) {
        serial = ai.incrementAndGet();

        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("serial", serial)
            .append("name", name)
            .append("age", age)
            .append("height", height)
            .append("weight", weight)
            .toString();
    }

}
