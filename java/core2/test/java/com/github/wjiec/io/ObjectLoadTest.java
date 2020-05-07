package com.github.wjiec.io;

import com.github.wjiec.human.NewPerson;
import com.github.wjiec.human.Person;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectLoadTest {

    public static void main(String[] args) {
        try (InputStream file = new FileInputStream("person_object.dat")) {
            ObjectInputStream serialization = new ObjectInputStream(file);

            try {
                // read correct
                Person p0 = (Person)serialization.readObject(); // Person -> Person
                System.out.println(p0);

                // read incorrect
                Person p1 = (Person)serialization.readObject(); // NewPerson -> Person
                System.out.println(p1);
            } catch (ClassNotFoundException|ClassCastException e) {
                e.printStackTrace();
            }

            try {
                // read incorrect
                NewPerson p0 = (NewPerson)serialization.readObject(); // Person -> NewPerson
                System.out.println(p0);
            } catch (ClassNotFoundException|ClassCastException e) {
                e.printStackTrace();
            }

            try {
                // read incorrect
                NewPerson p1 = (NewPerson)serialization.readObject(); // NewPerson -> NewPerson
                System.out.println(p1);
            } catch (ClassNotFoundException|ClassCastException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
