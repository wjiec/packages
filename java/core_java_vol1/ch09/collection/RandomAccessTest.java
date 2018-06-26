package collection;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.RandomAccess;

public class RandomAccessTest {
    public static void main(String[] args) {
        Queue<Integer> queue1 = new ArrayDeque<>();
        Queue<Integer> queue2 = new LinkedList<>();

        if (queue1 instanceof RandomAccess) {
            System.out.println("ArrayDeque supported high performance random-access");
        }

        if (queue2 instanceof RandomAccess) {
            System.out.println("LinkedList supported high performance random-access");
        }
    }
}
