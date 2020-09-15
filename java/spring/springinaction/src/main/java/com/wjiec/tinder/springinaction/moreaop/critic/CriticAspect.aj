package com.wjiec.tinder.springinaction.moreaop.critic;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public aspect CriticAspect {

    public CriticAspect() {}

    pointcut performance() : execution(* com.wjiec.tinder.springinaction.moreaop.performance.Performance.perform(..));

    before() : performance() {
        if (criticismEngine != null) {
            System.out.println(criticismEngine.getCriticism());
        } else {
            System.out.println("...");
        }
    }

    private CriticismEngine criticismEngine;

    public void setCriticismEngine(CriticismEngine engine) {
        criticismEngine = engine;
    }

}
