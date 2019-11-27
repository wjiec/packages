package tests.interfaces.conflict;

import main.interfaces.conflict.Named;
import main.interfaces.conflict.OtherNamed;

public class Conflict3 implements Named, OtherNamed {

    // Overloading
    @Override
    public String getName() {
        return null;
    }

    // Overloading
    @Override
    public String getName(StringBuilder builder) {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new Conflict3().getName());
    }

}
