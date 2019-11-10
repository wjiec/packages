package OOP;

import java.util.Random;

public class ShutdownHook {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("resources recovery");
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Random generator = new Random();
        while (generator.nextInt(10000) != 5555) {
            // loop
        }
    }

}
