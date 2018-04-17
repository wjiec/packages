public class ConstructByAnother {

    private String name;

    private int age;

    public ConstructByAnother(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ConstructByAnother(int age) {
        this("Anonymous", age);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        builder.append(age);
        return builder.toString();
    }

    public static void main(String[] args) {
        ConstructByAnother object1 = new ConstructByAnother("Jayson", 21);
        ConstructByAnother object2 = new ConstructByAnother(21);

        System.out.println(object1);
        System.out.println(object2);
    }
}
