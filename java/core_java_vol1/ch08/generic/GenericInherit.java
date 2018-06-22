package generic;

public class GenericInherit extends PairClass<Integer, Integer> {
    private GenericInherit(Integer first, Integer second) {
        super(first, second);
    }

    void setSecond(Integer value) {
        if (value > getFirst()) {
            super.setSecond(value);
        }
    }

    public static void main(String[] args) {
        GenericInherit sample = new GenericInherit(1, 1);
        PairClass<Integer, Integer> alias = sample;

        alias.setSecond(0);
        System.out.println(alias.getSecond());

        alias.setSecond(1);
        System.out.println(alias.getSecond());

        alias.setSecond(2);
        System.out.println(alias.getSecond());
    }
}
