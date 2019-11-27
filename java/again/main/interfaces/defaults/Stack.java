package main.interfaces.defaults;

import java.util.ArrayList;
import java.util.Arrays;

public class Stack implements DefaultMethodInterface {

    private ArrayList<Object> stack;

    public Stack(Object... els) {
        stack = new ArrayList<>();
        stack.addAll(Arrays.asList(els));
    }

    @Override
    public int size() {
        return stack.size();
    }

}
