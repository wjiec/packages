package generic;

public class ClassGeneric {
    static <T, U> PairClass<T, U> makePair(Class<T> c1, Class<U> c2) throws
            IllegalAccessException, InstantiationException {
        return new PairClass<>(c1.newInstance(), c2.newInstance());
    }

    public static void main(String[] args) {
        try {
            // Integer.class is an instance of Integer class
            PairClass<String, String> value = makePair(String.class, String.class);

            System.out.println(value.getFirst());
            System.out.println(value.getSecond());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
