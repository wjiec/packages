package tests.interfaces.conflict;

import main.interfaces.conflict.Named;
import main.interfaces.conflict.OtherNamed;

public class Conflict4 extends Base implements Named, OtherNamed {

    @Override
    public String getName(StringBuilder builder) {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new Conflict4().getName());
    }

}
