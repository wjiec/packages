package main.plugin;

import main.plugin.service.Calculator;

import java.util.ServiceLoader;

public class Executor {

    public static void main(String[] args) {
        ServiceLoader<Calculator> loader = ServiceLoader.load(Calculator.class);
        for (Calculator calculator : loader) {
            calculator.calculate("value");
        }
    }

}
