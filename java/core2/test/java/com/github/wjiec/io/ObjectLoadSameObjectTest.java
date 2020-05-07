package com.github.wjiec.io;


import com.github.wjiec.human.Person;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ObjectLoadSameObjectTest {

    public static void main(String[] args) {
        try (InputStream file = new FileInputStream("person_object.dat")) {
            ObjectInputStream serialization = new ObjectInputStream(file);
            List<Person> people = (ArrayList<Person>)serialization.readObject(); // LinkedList -> ArrayList
            System.out.println(people);

            serialization.close();
        } catch (IOException|ClassNotFoundException|ClassCastException e) {
            e.printStackTrace();
        }

        try (InputStream file = new FileInputStream("person_object.dat")) {
            ObjectInputStream serialization = new ObjectInputStream(file);
            List<Person> people = (LinkedList<Person>)serialization.readObject(); // LinkedList -> LinkedList
            System.out.println(people);
            System.out.println(people.get(0).hashCode());
            System.out.println(people.get(1).hashCode());

            serialization.close();
        } catch (IOException|ClassNotFoundException|ClassCastException e) {
            e.printStackTrace();
        }
    }

}
