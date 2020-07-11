package com.wjiec.learn.join;

public class JoinRuleTest {

    public static void main(String[] args) {
        SharedObj obj = new SharedObj();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new JoinRule(obj));
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                System.out.println(threads[i].isAlive());
                threads[i].join();
                System.out.println(threads[i].isAlive());
                System.out.printf("join-%d completed\n", i);
                System.out.printf("join after value: %d\n", obj.get());
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
