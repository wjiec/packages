package generic;

import java.util.function.Supplier;

public class GenericInstanceType<T> {
    private T first;
    private T second;

//    GenericInstanceType() {
//        first = new T();
//        second = new T();
//    }

    GenericInstanceType(Supplier<T> supplier) {
        first = supplier.get();
        second = supplier.get();
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + "first=" + first + ",second=" + second + "]";
    }

    public static void main(String[] args) {
        GenericInstanceType<String> sample = new GenericInstanceType<>(String::new);
        System.out.println(sample);
    }
}
