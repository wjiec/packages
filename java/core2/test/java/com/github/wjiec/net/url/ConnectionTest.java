package com.github.wjiec.net.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ConnectionTest {

    public static void main(String[] args) {
        try {
            URI uri = new URI("https://httpbin.org/get?a=1&b=2");
            URL url = uri.toURL();

            URLConnection connection = url.openConnection();
            connection.connect();

            for (var e : connection.getHeaderFields().entrySet()) {
                if (e.getKey() == null) {
                    System.out.printf("StatusLine -> %s\n", e.getValue());
                } else {
                    System.out.printf("Header -> %s: %s\n", e.getKey(), e.getValue());
                }
            }

            System.out.println();
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
