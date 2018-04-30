package interfaces;

import java.util.Arrays;

public class ComparableError {
    public static void main(String[] args) {
        ComparableError[] errors = {
            new ComparableError(1),
            new ComparableError(3),
            new ComparableError(2)
        };

        Arrays.sort(errors);
        for (ComparableError e : errors) {
            System.out.println(e);
        }
    }

    private ComparableError(int count) {
        this.count = count;
    }

    private int count;
}
