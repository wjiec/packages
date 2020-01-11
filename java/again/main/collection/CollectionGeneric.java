package main.collection;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionGeneric {

    public static void main(String[] args) {
        Collection<Integer> collection = new LinkedList<>();
        System.out.println(collection.isEmpty());
        System.out.println(collection.add(1));
        System.out.println(collection.isEmpty());
    }

}
