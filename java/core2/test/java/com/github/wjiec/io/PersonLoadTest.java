package com.github.wjiec.io;

import com.github.wjiec.human.Person;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PersonLoadTest {

    public static void main(String[] args) {
        try (var file = new FileInputStream("person.dat")) {
            var reader = new PersonReader(new BufferedInputStream(file));

            Person person;
            while ((person = reader.read()) != null) {
                System.out.println(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
