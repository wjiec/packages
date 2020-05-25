package com.github.wjiec.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringJoiner;

public class NetTime {

    public static String now(String host, int timeout) {
        StringJoiner joiner = new StringJoiner("\n");
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(host, 13), timeout);

            Scanner scanner = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
            while (scanner.hasNext()) {
                joiner.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return joiner.toString();
    }

    public static String now(String host) {
        return now(host, 1000);
    }

    public static String now() {
        return now("0.asia.pool.ntp.org");
    }

}
