package com.wjiec.tinder.springinaction.moreaop.performance;

import org.springframework.stereotype.Component;

@Component
public class Concert implements Performance {

    @Override
    public void perform() {
        System.out.println("perform concert");
    }

}
