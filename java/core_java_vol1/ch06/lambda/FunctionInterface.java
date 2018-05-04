package lambda;

import javax.swing.*;
import java.util.Arrays;

public class FunctionInterface {
    public static void main(String[] args) {
        Timer t = new Timer(1000, System.out::println);
        t.start();

        JOptionPane.showMessageDialog(null, "exit ?");
//        System.exit(0);

        String[] names = {
            "J",
            "Jim",
            "Jane"
        };
        // String::compareToIgnoreCase => (x, y) -> x.compareToIgnoreCase(y)
        Arrays.sort(names, String::compareToIgnoreCase);
    }
}
