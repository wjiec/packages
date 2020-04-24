package tests.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WorkQueueReader {

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> works = new ArrayBlockingQueue<>(32);

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    works.put((int)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Integer work = works.poll(100, TimeUnit.MILLISECONDS);
                        if (work == null) {
                            break;
                        }

                        System.out.println("do working: " + work);
                        Thread.sleep(work);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
