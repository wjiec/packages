package tests.interfaces.defaults;

import main.interfaces.defaults.Stack;

public class StackTest {

    public static void main(String[] args) {
        Stack s0 = new Stack();
        System.out.println(s0.isEmpty());

        Stack s1 = new Stack(1, 2.33, false);
        System.out.println(s1.isEmpty());
    }

}
