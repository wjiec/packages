package tests.concurrent;

public class SynchronizedReentrant {

    synchronized void step1(int index) throws InterruptedException {
        System.out.println("step1 " + index);
        step2(index);
    }

    synchronized void step2(int index) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("step2");
    }

    public static void main(String[] args) {
        SynchronizedReentrant instance = new SynchronizedReentrant();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    instance.step1(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
