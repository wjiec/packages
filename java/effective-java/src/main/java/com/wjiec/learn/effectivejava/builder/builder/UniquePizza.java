package com.wjiec.learn.effectivejava.builder.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.concurrent.atomic.AtomicInteger;

public class UniquePizza extends Pizza {
    private static final AtomicInteger serial = new AtomicInteger(0);
    private final int id;

    public UniquePizza(Builder builder) {
        super(builder);
        this.id = builder.id;
    }

    public static class Builder extends Pizza.Builder<Builder> {
        private final int id;

        public Builder() {
            id = serial.incrementAndGet();
        }

        @Override
        public Pizza build() {
            return new UniquePizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("parent", super.toString())
            .append("id", id)
            .toString();
    }
}
