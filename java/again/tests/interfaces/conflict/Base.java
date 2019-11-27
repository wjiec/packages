package tests.interfaces.conflict;

import main.interfaces.conflict.Named;

public class Base implements Named {

    @Override
    public String getName() {
        return "Base";
    }

    public static void main(String[] args) {
        System.out.println(new Base().getName());
    }

}
