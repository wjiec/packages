package com.github.wjiec.io;

import com.github.wjiec.human.NewPerson;
import com.github.wjiec.human.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectDumpTest {

    public static void main(String[] args) {
        try (OutputStream file = new FileOutputStream("person_object.dat")) {
            ObjectOutputStream serialization = new ObjectOutputStream(file);
            serialization.writeObject(new Person("A", 0));
            serialization.writeObject(new NewPerson("B", 1, 0));
            serialization.writeObject(new Person("C", 2));
            serialization.writeObject(new NewPerson("D", 3, 1));

            serialization.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
