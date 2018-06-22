package generic;

public class PairClass<T, U> {
    private T first;
    private U second;

    PairClass() {
        this.first = null;
        this.second = null;
    }

    PairClass(T first, U second) {
        this.first = first;
        this.second = second;
    }

    T getFirst() {
        return this.first;
    }

    U getSecond() {
        return this.second;
    }

    void setFirst(T value) {
        this.first = value;
    }

    void setSecond(U value) {
        this.second = value;
    }

    public static void main(String[] args) {
        PairClass<Integer, String> pair = new PairClass<>(1, "aaa");

        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
    }
}
