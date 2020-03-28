package main.concurrent;

import java.io.InputStream;
import java.util.Scanner;

public class Reader implements Runnable {

    private InputStream stream;

    public Reader(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public void run() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        try (Scanner scanner = new Scanner(stream)) {
            while (true) {
                if (scanner.hasNext()) {
                    int index = scanner.nextInt();
                    System.out.println(index);
                    System.out.println(numbers[index]);
                }

                Thread.sleep(23);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
