package main.collection.view;

import java.util.*;

public class ModifyView {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        List<String> ulist = Collections.unmodifiableList(list);
        list.add("e");

        ulist.forEach(System.out::println);
    }

}
