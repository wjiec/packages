package com.wjiec.learn.effectivejava.builder.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NyPizza extends Pizza {
    private final Size size;

    public NyPizza(Builder builder) {
        super(builder);

        this.size = builder.size;
    }

    public enum Size {SMALL, MEDIUM, LARGE}
    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = size;
        }

        @Override
        public Pizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("parent", super.toString())
            .append("size", size)
            .toString();
    }
}
