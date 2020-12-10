package com.wjiec.springaio.xmlwiring.model;

import lombok.Data;

@Data
public class Author {

    private String name;
    private int age;

    public Author(String name) {
        this.name = name;
    }

}
