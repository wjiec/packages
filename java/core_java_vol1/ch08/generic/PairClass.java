package generic;

public class PairClass<T, U> {
    private T first;
    private U second;

    private PairClass() {
        this.first = null;
        this.second = null;
    }

    private PairClass(T first, U second) {
        this.first = first;
        this.second = second;
    }

    private T getFirst() {
        return this.first;
    }

    private U getSecond() {
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
