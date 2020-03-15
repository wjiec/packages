package main.collection.view;

import java.util.*;

public class ViewSet {

    public static void main(String[] args) {
        String[] strings = new String[] {"a", "b", "c", "d"};
        List<String> list = Arrays.asList(strings);
        for (String s : list) {
            System.out.println(s);
        }

        Iterator<String> iterator = list.iterator();
        System.out.println(iterator.next());
        iterator.remove();
    }

}
