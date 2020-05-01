package com.github.wjiec.stream;

import com.github.wjiec.human.Person;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectTest {

    public static void main(String[] args) {
        Map<Integer, String> map0 = Person.random().limit(10).collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println(map0);

        Map<Integer, Person> map1 = Person.random().limit(10).collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println(map1);
    }

}
