package collection;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;

public class ListIteratorSample {
    public static void main(String[] args) {
        LinkedList<String> queue = new LinkedList<>();
        queue.add("A");
        queue.add("B");
        queue.add("C");
        queue.add("D");
        queue.add("E");

        ListIterator<String> iterator1 = queue.listIterator();
        ListIterator<String> iterator2 = queue.listIterator();

        iterator1.next();
        iterator1.remove();
        System.out.println(iterator1.next());
        try {
            System.out.printf(iterator2.next());
        } catch (ConcurrentModificationException e) {
            System.out.println(e.toString());
        }

        ListIterator<String> iterator3 = queue.listIterator(queue.size() - 1);
        System.out.println(iterator3.next());
        System.out.println(iterator3.previous());
        System.out.println(iterator3.previous());
    }
}
