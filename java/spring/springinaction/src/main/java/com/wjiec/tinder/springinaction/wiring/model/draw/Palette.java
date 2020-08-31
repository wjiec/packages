package com.wjiec.tinder.springinaction.wiring.model.draw;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class Palette {

    private List<String> colors;
    private Set<String> primaryColors;

    public  Palette(List<String> colors, Set<String> primaryColors) {
        this.colors = colors;
        this.primaryColors = primaryColors;
    }

    public List<String> getColors() {
        return colors;
    }

    public Set<String> getPrimaryColors() {
        return primaryColors;
    }
}
