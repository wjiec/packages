package com.wjiec.springaio.autowiring.worker;

import com.wjiec.springaio.autowiring.tool.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Treasurer implements Worker {

    private final Calculator calculator;

    @Autowired(required = false)
    public Treasurer(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void work() {
        System.out.printf("The treasurer working by Calculator(name=%s)\n",
            calculator.getName());
    }

}
