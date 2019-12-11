package main.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstructorReference {

    public static void main(String[] args) {
        String[] strings = new String[]{"a", "b", "c"};
        Stream<Person> steam = Arrays.stream(strings).map(Person::new);
        List<Person> people = steam.collect(Collectors.toList());
        for (Person person : people) {
            System.out.println(person.name);
        }

        Integer[] integers = new Integer[]{1, 2, 3};
        steam = Arrays.stream(integers).map(Person::new);
        people = steam.collect(Collectors.toList());
        for (Person person : people) {
            System.out.println(person.name);
        }

//        Object[] objects = new Object[]{1, "string", 3};
//        steam = Arrays.stream(objects).map(Person::new);
    }

}

class Person {

    public String name;

    Person(int n) {
        name = "Number: " + n;
    }

    Person(String n) {
        name = n;
    }

}
