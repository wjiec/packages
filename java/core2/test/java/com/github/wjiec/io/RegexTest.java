package com.github.wjiec.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) throws IOException {
        InputStream s = new URL("http://httpbin.org/get?p0=123&p1=abc").openStream();
        Pattern.compile("(\\w+[^\\w]\\w+)", Pattern.MULTILINE)
            .matcher(new String(s.readAllBytes(), StandardCharsets.UTF_8))
            .results()
            .map(MatchResult::group)
            .forEach(System.out::println);
    }

}
