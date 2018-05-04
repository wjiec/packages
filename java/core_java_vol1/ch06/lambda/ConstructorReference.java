package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstructorReference {
    private String name;

    private ConstructorReference(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s[name=%s]", getClass().getName(), name);
    }

    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            names.add("Name_" + i);
        }

        Stream<ConstructorReference> stream = names.stream().map(ConstructorReference::new);
        List<ConstructorReference> objs = stream.collect(Collectors.toList());
        for (ConstructorReference obj : objs) {
            System.out.println(obj);
        }
    }
}
