package tests.interfaces.conflict;

import main.interfaces.conflict.Named;

public class Conflict1 extends Base implements Named {

    public static void main(String[] args) {
        System.out.println(new Conflict1().getName());
    }

}
