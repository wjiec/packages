package com.wjiec.learn.effectivejava.cloneable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Element implements Cloneable {

    private final int number;
    private Holder holder;

    public Element(int number) {
        this.number = number;
        this.holder = new Holder();
    }

    @Override
    protected Element clone() throws CloneNotSupportedException {
        try {
            Element element = (Element) super.clone();
            element.holder = new Holder();

            return element;
        } catch (CloneNotSupportedException ignored) {}
        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("number", number)
            .append("holder", holder)
            .toString();
    }

    public static class Holder { }

}
