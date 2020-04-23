package tests.concurrent;

public class VolatileTest {

    private volatile int number;

    private VolatileTest() {
        number = 10;
    }

    private int getNumber() {
        return number;
    }

    private void setNumber(int number) {
        this.number = number;
    }

    public static void main(String[] args) {
        VolatileTest instance = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                instance.setNumber(finalI);
                System.out.println(instance.getNumber());
            }).start();
        }
    }

}
