package main.collection.iterator;

import java.util.*;
import java.util.stream.Collectors;

public class ReviewAfterRemove {

    public static void main(String[] args) {
        String[] strings = new String[]{"a", "b", "c", "d", "e", "f"};
        List<String> list = Arrays.stream(strings).collect(Collectors.toCollection(LinkedList::new));

        ListIterator<String> iterator = list.listIterator();
        System.out.println(iterator.next()); // a ^ b c d e f
        System.out.println(iterator.next()); // a b ^ c d e f
        iterator.remove(); // a ^ c d e f
        System.out.println(iterator.previous()); // ^ a c d e f
        iterator.remove(); // c d e f
        System.out.println(iterator.next()); // c ^ d e f
        System.out.println(iterator.next()); // c d ^ e f

        System.out.println(list);
    }

}
