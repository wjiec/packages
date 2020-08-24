package com.wjiec.learn.effectivejava.builder.javabeans;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.concurrent.atomic.AtomicInteger;

public class Monster {

    static final AtomicInteger ai = new AtomicInteger(0);

    private final long serial;
    private String name;
    private int age;
    private double height;
    private double weight;

    public Monster() {
        serial = ai.incrementAndGet();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
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
