package main.collection.hashset;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SetTest {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new FileInputStream("aliceinwonderland.txt"))) {
            Set<String> words = new HashSet<>();
            while (scanner.hasNext()) {
                words.add(scanner.next());
            }

            System.out.println("Size of words: " + words.size());

            Iterator<String> iterator = words.iterator();
            for (int i = 0; i < 50 && iterator.hasNext(); i++) {
                System.out.println(iterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
