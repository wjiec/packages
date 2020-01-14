package main.collection.iterator;

import main.collection.list.Printer;

import java.util.*;
import java.util.stream.Collectors;

public class IteratorAdd {

    public static void main(String[] args) {
        String[] strings = new String[]{"a", "b", "c"};
        List<String> list = Arrays.stream(strings).collect(Collectors.toCollection(LinkedList::new));

        Iterator<String> traveler = list.iterator();
        ListIterator<String> modifier = list.listIterator();
        modifier.add("add0");

        modifier.next();
        modifier.add("add1");

        modifier.next();
        modifier.next();
        modifier.add("add2");
        modifier.add("add3");

        Printer.print(list);
        System.out.println(traveler.next()); // here
    }

}
