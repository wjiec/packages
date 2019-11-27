package tests.interfaces.conflict;

import main.interfaces.conflict.DefaultNamed;
import main.interfaces.conflict.Named;

public class Conflict0 implements Named, DefaultNamed {

    // Error:(6, 8) java: tests.interfaces.conflict.Conflict0
    // is not abstract and does not override abstract method getName() in main.interfaces.conflict.Named
    @Override
    public String getName() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new Conflict0().getName());
    }

}
