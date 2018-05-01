package interfaces;

public interface Person {
    default String getName() {
        return "Person Interface";
    }

    int getAge(int additional);
}
