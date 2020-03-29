package tests.concurrent;

import main.concurrent.Pipes;

public class PipesTest {

    private static final int PIPE_COUNT = 8;

    public static void main(String[] args) {
        Pipes pipes = new Pipes(PIPE_COUNT);

        for (int i = 0; i < 10; ++i) {
            final int t = i;
            new Thread(() -> {
                while (true) {
                    int to = (int)(Math.random() * PIPE_COUNT);
                    System.out.printf(">>> message to %d: %s\n", to, "message from " + t);
                    pipes.write(to, "message from " + t);
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    int from = (int)(Math.random() * PIPE_COUNT);
                    System.out.printf("<<< message from %d: %s\n", t, pipes.read(from));
                }
            }).start();
        }
    }

}
