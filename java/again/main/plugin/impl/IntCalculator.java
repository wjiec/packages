package main.plugin.impl;

import main.plugin.service.Calculator;

public class IntCalculator implements Calculator {

    @Override
    public <T> void calculate(T value) {
        System.out.println("IntCalculator: " + value);
    }

}
