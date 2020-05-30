package com.github.wjiec.net.url;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class PostTest {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://httpbin.org/post");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);

            connection.setRequestProperty("User-Agent", "Chrome/80.0.3987.163");
            connection.setRequestProperty("X-Trace-Id", UUID.randomUUID().toString());

            HashMap<String, String> form = new HashMap<>();
            form.put("a", "1");
            form.put("b", "2");

            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            for (var e : form.entrySet()) {
                writer.printf("%s=%s&", e.getKey(), URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8));
            }
            writer.close();

            StringBuilder builder = new StringBuilder();
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                while (scanner.hasNext()) {
                    builder.append(scanner.next());
                }
            }

            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
