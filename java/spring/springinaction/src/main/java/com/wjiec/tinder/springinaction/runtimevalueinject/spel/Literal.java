package com.wjiec.tinder.springinaction.runtimevalueinject.spel;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ToString
public class Literal {

    private int start;
    private int end;
    private String tips;
    private int number;
    private boolean bool;
    private String s;

    public Literal(@Value("#{systemProperties['range.start']}") int start,
                   @Value("#{systemProperties['range.end']}") int end,
                   @Value("#{systemProperties['range.alert']}") String tips,
                   @Value("#{1}") int number,
                   @Value("#{true}") boolean bool,
                   @Value("#{'string'}") String s) {
        this.start = start;
        this.end = end;
        this.tips = tips;
        this.number = number;
        this.bool = bool;
        this.s = s;
    }

}
