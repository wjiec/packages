package com.wjiec.tinder.springinaction.runtimevalueinject.envplaceholder;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ToString
public class Range {

    private int start;
    private int end;
    private String alert;

    public Range(@Value("${range.start}") int start, @Value("${range.end}") int end, @Value("${range.alert}") String alert) {
        this.start = start;
        this.end = end;
        this.alert = alert;
    }

}
