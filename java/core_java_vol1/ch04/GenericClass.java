import java.util.ArrayList;

public class GenericClass {
    public static void main(String[] args) {
        ArrayList<GenericClass> genericClasses = new ArrayList<>();

        while (genericClasses.size() < 100) {
            genericClasses.add(new GenericClass());
        }

        for (GenericClass genericClass : genericClasses) {
            System.out.print(genericClass + " ");
        }

        for (int i = 0; i < genericClasses.size(); ++i) {
            if (i % 20 == 0) {
                System.out.println();
            }
            System.out.print(genericClasses.get(i) + " ");
        }
    }
}
