package collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class IteratorSample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < 10; ++i) {
            queue.add("String" + i);
        }

        Iterator<?> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        iterator = queue.iterator();
        iterator.forEachRemaining(e -> System.out.println(e));

        iterator = queue.iterator();
        iterator.forEachRemaining(System.out::println);
    }
}
