package com.wjiec.learn.threading.communicate;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedSameType {

    public static void main(String[] args) throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();
//        in.connect(out);
        out.connect(in);

        Thread printer = new Thread(() -> {
            int received = 0;
            while (true) {
                try {
                    if ((received = in.read()) == -1) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println((char)received);
            }
        });
        printer.start();

        int received;
        try {
            while ((received = System.in.read()) != -1) {
                out.write(received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
