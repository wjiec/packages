package com.github.wjiec.io;

import com.github.wjiec.human.NewPerson;
import com.github.wjiec.human.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class ObjectDumpSameObjectTest {

    public static void main(String[] args) {
        List<Person> people = new LinkedList<>();
        NewPerson person = new NewPerson("A", 24, 174);
        people.add(person);
        people.add(person);
        System.out.println(people.get(0) == people.get(1));

        try (OutputStream file = new FileOutputStream("person_object.dat")) {
            ObjectOutputStream serialization = new ObjectOutputStream(file);
            serialization.writeObject(people);

            serialization.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
