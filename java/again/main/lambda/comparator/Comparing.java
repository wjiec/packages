package main.lambda.comparator;

import abstracts.human.Employee;
import abstracts.human.Person;
import abstracts.human.Student;

import java.util.Arrays;
import java.util.Comparator;

public class Comparing {

    public static void main(String[] args) {
        Person[] people = new Person[]{
            new Student("Jack", "English"),
            new Student("Lisa", "English"),
            new Student("John", "English"),
            new Employee("Lily", 1000),
            new Employee("Ana", 2000),
            new Employee("Cat", 3000),
        };

        Arrays.sort(people, Comparator.comparing(Person::getName));
        System.out.println(Arrays.toString(people));

        Arrays.sort(people, Comparator.comparing(Person::getDescription,
            (l, r) -> Integer.compare(l.length(), r.length())));
        System.out.println(Arrays.toString(people));

        Arrays.sort(people, Comparator.comparing(Person::getDescription, Comparator.comparingInt(String::length)));
        System.out.println(Arrays.toString(people));

        Arrays.sort(people, Comparator.comparing(Person::getDescription, Comparator.comparingInt(String::length))
            .thenComparing(Person::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
        System.out.println(Arrays.toString(people));
    }

}
