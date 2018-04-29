package reflection;


import java.util.ArrayList;

public class ObjectAnalyzerTest {
    public static void main(String[] args) {
        ArrayList<Apple> numbers = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            numbers.add(new Apple());
        }

        System.out.println(new ObjectAnalyzer().toString(numbers));
    }
}
