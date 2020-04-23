package tests.concurrent;

public class SynchronizedStatic {

    private synchronized static void step1(int index) throws InterruptedException {
        System.out.println("step1 " + index);
        step2(index);
    }

    private synchronized static void step2(int index) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("step2");

        SynchronizedReentrant sync = new SynchronizedReentrant();
        sync.step1(index);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    step1(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
