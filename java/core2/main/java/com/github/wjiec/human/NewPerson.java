package com.github.wjiec.human;

import java.util.StringJoiner;

public class NewPerson extends Person {

    private int height;

    public NewPerson(String name, int age, int height) {
        super(name, age);

        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ")
            .add(super.toString())
            .add(String.valueOf(height))
            .toString();
    }

}
