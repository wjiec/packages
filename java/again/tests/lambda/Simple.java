package tests.lambda;

import java.util.Arrays;
import java.util.Comparator;

public class Simple {

    public static void main(String[] args) {
        Comparator<String> comparator = (String s1, String s2) -> s1.length() - s2.length();
        String[] ss = new String[]{"c", "b", "a"};
        Arrays.sort(ss, comparator);
        Arrays.sort(ss, (String s1, String s2) -> s1.length() - s2.length());
        System.out.println(Arrays.toString(ss));

        System.out.println(comparator instanceof Object);

        Object o = comparator;
        System.out.println(o);
    }

}
