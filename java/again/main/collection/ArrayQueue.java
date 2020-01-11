package main.collection;

import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayQueue {

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>(4);
        System.out.println(queue.add(1));;
        System.out.println(queue.add(2));;
        System.out.println(queue.add(3));;
        System.out.println(queue.add(4));;
        System.out.println(queue.add(5));;
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }

}
