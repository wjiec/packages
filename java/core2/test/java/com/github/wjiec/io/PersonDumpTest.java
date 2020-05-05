package com.github.wjiec.io;

import com.github.wjiec.human.Person;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonDumpTest {

    public static void main(String[] args) {
        try (var file = new FileOutputStream("person.dat")) {
            var writer = new PersonWriter(new BufferedOutputStream(file));
            Person.random().limit(100).forEach((p) -> {
                try {
                    System.out.println(p);
                    writer.write(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
