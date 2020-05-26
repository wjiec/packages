package com.github.wjiec.net.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerClientTest {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 9989);
            new Thread(() -> {
                try (Scanner scanner = new Scanner(s.getInputStream(), StandardCharsets.UTF_8)) {
                    while (scanner.hasNext()) {
                        System.out.println(scanner.next());
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }).start();

            var writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true);

            writer.println("hello");
            writer.println("world");
            writer.println("from");
            writer.println("client");
            writer.println(InetAddress.getLocalHost());
            s.shutdownOutput();

            Thread.sleep(2000);
            s.close();
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }

}
