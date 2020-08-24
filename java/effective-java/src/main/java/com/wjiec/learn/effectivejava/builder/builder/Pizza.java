package com.wjiec.learn.effectivejava.builder.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.Set;

public abstract class Pizza {
    private Set<Topping> toppings;

    public Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }

    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
    abstract static class Builder<T extends Builder<T>> {
        private final EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(topping);
            return self();
        }

        public abstract Pizza build();

        protected abstract T self();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("toppings", toppings)
            .toString();
    }

}
