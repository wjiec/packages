/**
 * Created by ShadowMan on 2017/4/5.
 */

public class TestClass {
    public static void main(String[] args) {
        Person person = new Person("Jack", 20);
    }

    private static class Person {
        public Person(String name, int age) {
            System.out.printf("Hello, my name is %s, I'm %d years old.", name, age);
        }
    }
}
