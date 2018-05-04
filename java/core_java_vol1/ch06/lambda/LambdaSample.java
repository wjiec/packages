package lambda;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class LambdaSample {
    public static void main(String[] args) {
        String[] names = {"J", "Jim", "Jane"};
        Arrays.sort(names, Comparator.comparingInt(String::length));
        for (String name : names) {
            System.out.println(name);
        }

        Timer t = new Timer(1000, e -> System.out.println(new Date()));
        t.start();

        JOptionPane.showMessageDialog(null, "Exit?");
        System.exit(0);
    }
}
