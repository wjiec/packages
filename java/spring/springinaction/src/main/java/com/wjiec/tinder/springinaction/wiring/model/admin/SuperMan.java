package com.wjiec.tinder.springinaction.wiring.model.admin;

import lombok.ToString;

@ToString
public class SuperMan implements Admin {

    private String name;
    private int age;

    public SuperMan(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void dance() {
        System.out.printf("%s learning dance at %d years old.\n", name, age);
    }

}
