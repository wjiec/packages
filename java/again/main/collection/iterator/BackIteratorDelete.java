package main.collection.iterator;

import main.collection.list.Printer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class BackIteratorDelete {

    // remove 会删除经过的那个元素
    public static void main(String[] args) {
        String[] strings = new String[]{"a", "b", "c", "d", "e", "f"};
        List<String> list = Arrays.stream(strings).collect(Collectors.toCollection(LinkedList::new));
        ListIterator<String> modifier = list.listIterator();

        modifier.next();
        modifier.remove(); // delete a
        Printer.print(list); // ^ b c d e f

        modifier.next();
        modifier.next();
        modifier.next(); // b c d ^ e f
        modifier.previous(); // b c ^ d e f
        modifier.remove(); // delete d
        Printer.print(list); // b c ^ e f

        System.out.println(modifier.next()); // e
    }

}
