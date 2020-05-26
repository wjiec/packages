package com.github.wjiec.net.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerTest {

    public static void main(String[] args) throws IOException {
        Server server = new Server(9989);
        server.serve((socket -> {
            try (Scanner scanner = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8)) {
                while (scanner.hasNext()) {
                    var s = scanner.next();
                    System.out.println(s);
                    new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true).println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

}
