package main.lambda;

public interface Abstract2MethodWithDefault1 {

    int compare(int left, int right);

    default int compare(double left, double right) {
        if (left == right) {
            return 0;
        } else if (left - right > 0) {
            return 1;
        }
        return -1;
    }

}
