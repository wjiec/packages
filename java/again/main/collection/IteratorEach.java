package main.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class IteratorEach {

    public static void main(String[] args) {
        Collection<?> list = Arrays.stream(new Integer[]{1, 2, 3, 4, 5}).collect(Collectors.toList());
        Collection<?> collection = new LinkedList<>(list);

        printCollection(collection);
        collection.iterator().forEachRemaining(System.out::println);
    }

    private static <T> void printCollection(Collection<? extends T> collection) {
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator: " + iterator.next());
        }
    }

}
