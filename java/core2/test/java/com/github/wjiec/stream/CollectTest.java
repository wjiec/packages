package com.github.wjiec.stream;

import com.github.wjiec.human.Person;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectTest {

    public static void main(String[] args) {
        Map<Integer, String> map0 = Person.random().limit(10).collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println(map0.getClass().getName());
        System.out.println(map0);

        Map<Integer, Person> map1 = Person.random().limit(10).collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println(map1.getClass().getName());
        System.out.println(map1);

        Map<String, Set<String>> map2 = Stream.of(Locale.getAvailableLocales()).collect(
            Collectors.toMap(Locale::getDisplayLanguage, n -> Collections.singleton(n.getDisplayCountry()), (a, b) -> {
                var union = new HashSet<>(a);
                union.addAll(b);

                return union;
            }, TreeMap::new)
        );
        System.out.println(map2.getClass().getName());
        System.out.println(map2);
    }

}
