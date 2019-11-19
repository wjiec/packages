package main.wrapper;

public class Boxing {

    public static void main(String[] args) {
        Integer n0 = 65536;
        System.out.println(n0.byteValue());
        System.out.println(n0.shortValue());

        n0++;
        System.out.println(n0.byteValue());
        System.out.println(n0.shortValue());

        Integer n1 = 999;
        Integer n2 = 999;
        System.out.println(n1 == n2);
        System.out.println(n1.equals(n2));

        n1 = 127;
        n2 = 127;
        System.out.println(n1 == n2);
        System.out.println(n1.equals(n2));

        Integer n3 = 1;
        Double d0 = 2.0;
        System.out.println(true ? n3 : d0);
    }

}
