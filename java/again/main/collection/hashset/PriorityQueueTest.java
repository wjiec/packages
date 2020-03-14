package main.collection.hashset;

import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueTest {

    public static void main(String[] args) {
        Queue<LocalDate> dates = new PriorityQueue<>();
        dates.add(LocalDate.of(2020, 1, 1));
        dates.add(LocalDate.of(2019, 1, 1));
        dates.add(LocalDate.of(2019, 12, 1));
        dates.add(LocalDate.of(2019, 12, 12));
        dates.add(LocalDate.of(2020, 5, 1));
        dates.add(LocalDate.of(2020, 5, 11));
        dates.add(LocalDate.of(2020, 5, 21));
        dates.add(LocalDate.of(2020, 5, 31));
        dates.add(LocalDate.of(2020, 5, 2));

        // unsorted
        for (LocalDate l : dates) {
            System.out.println(l);
        }

        System.out.println();
        while (!dates.isEmpty()) {
            System.out.println(dates.remove());
        }
    }

}
