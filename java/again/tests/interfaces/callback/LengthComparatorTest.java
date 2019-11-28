package tests.interfaces.callback;

import main.interfaces.callback.LengthComparator;

import java.util.Arrays;

public class LengthComparatorTest {

    public static void main(String[] args) {
        String[] people = new String[]{"Lisa", "Jack", "Sara"};
        Arrays.sort(people, new LengthComparator());
        System.out.println(Arrays.toString(people));
    }

}
