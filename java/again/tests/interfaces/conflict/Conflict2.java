package tests.interfaces.conflict;

import main.interfaces.conflict.DefaultNamed;

public class Conflict2 extends Base implements DefaultNamed {

    public static void main(String[] args) {
        System.out.println(new Conflict2().getName());
    }

}
