package com.wjiec.springaio.autowiring.worker;

import com.wjiec.springaio.autowiring.tool.Keyboard;
import com.wjiec.springaio.autowiring.tool.Mouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Programmer implements Worker {

    private final Keyboard keyboard;
    private Mouse mouse;

    @Autowired
    public Programmer(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Autowired(required = false)
    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override
    public void work() {
        System.out.printf("The programmer is working with keyboard(name=%s) and mouse(name=%s)\n",
            keyboard.getName(), mouse == null ? "NONE" : mouse.getName());
    }

}
