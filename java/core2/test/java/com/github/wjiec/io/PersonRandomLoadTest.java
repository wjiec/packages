package com.github.wjiec.io;

import java.io.IOException;

public class PersonRandomLoadTest {

    public static void main(String[] args) {
        try {
            PersonRandomReader reader = new PersonRandomReader("person.dat");
            System.out.println(reader.read(0));
            System.out.println(reader.read(1));
            System.out.println(reader.read(50));
            System.out.println(reader.read(99));
            System.out.println(reader.read(100));
            System.out.println(reader.read(101));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
