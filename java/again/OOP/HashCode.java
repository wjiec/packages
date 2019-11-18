package OOP;

public class HashCode {

    public static void main(String[] args) {
        String s1 = "String1";
        StringBuilder sb1 = new StringBuilder(s1);
        System.out.println(s1.hashCode());
        System.out.println(sb1.hashCode());

        String s2 = new String("String1");
        StringBuilder sb2 = new StringBuilder(s2);
        System.out.println(s2.hashCode());
        System.out.println(sb2.hashCode());
    }

}
