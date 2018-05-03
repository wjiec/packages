package interfaces;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorSample {
    public static void main(String[] args) {
        String[] names = {
            "Jane",
            "J",
            "Kim",
            "Banana"
        };

        Arrays.sort(names, new StringLengthComparator());
        for (String name : names) {
            System.out.println(name);
        }
    }
}


class StringLengthComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return Integer.compare(o1.length(), o2.length());
    }
}
