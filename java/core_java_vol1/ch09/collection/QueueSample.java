package collection;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;


public class QueueSample {
    public static void main(String[] args) {
        Queue<String> queue1 = new ArrayDeque<>(4);
        try {
            queue1.add("string1");
            queue1.add("string2");
            queue1.add("string3");
            queue1.add("string4");
            queue1.add("string5");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.toString());
        }

        try {
            System.out.println(queue1.remove());
            System.out.println(queue1.remove());
            System.out.println(queue1.remove());
            System.out.println(queue1.remove());
            System.out.println(queue1.remove());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.toString());
        }

        Queue<String> queue2 = new LinkedList<>();
        for (int i = 0; i < 10000; ++i) {
            queue2.add("string" + i);
        }
        System.out.println(queue2.size());
    }
}
