package main.plugin.impl;

import main.plugin.service.Calculator;

public class FloatCalculator implements Calculator {

    @Override
    public <T> void calculate(T value) {
        System.out.println("FloatCalculator: " + value);
    }

}
