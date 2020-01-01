package main.asserts;


// java -ea main.asserts.SimpleAssert
// java -enableassertions main.asserts.SimpleAssert
public class SimpleAssert {

    public static void main(String[] args) {
        int i = 0;
        assert i == 0;
        System.out.println(i);

        int j = 1;
        assert j == 0;
        System.out.println(j);
    }

}
