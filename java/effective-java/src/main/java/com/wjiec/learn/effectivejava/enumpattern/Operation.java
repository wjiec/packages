package com.wjiec.learn.effectivejava.enumpattern;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Operation {

    PLUS("+") {
        @Override
        public <T extends Number> double apply(T l, T r) {
            return l.doubleValue() + r.doubleValue();
        }
    },
    MINUS("-") {
        @Override
        public <T extends Number> double apply(T l, T r) {
            return l.doubleValue() - r.doubleValue();
        }
    },
    TIMES("*") {
        @Override
        public <T extends Number> double apply(T l, T r) {
            return l.doubleValue() * r.doubleValue();
        }
    },
    DIVIDE("/") {
        @Override
        public <T extends Number> double apply(T l, T r) {
            return l.doubleValue() / r.doubleValue();
        }
    };

    private final String symbol;
    private final static Map<String, Operation> operationMap = Stream.of(values())
        .collect(Collectors.toMap(Operation::toString, Function.identity()));

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public abstract <T extends Number> double apply(T l, T r);

    @Override
    public String toString() {
        return symbol;
    }

    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(operationMap.get(symbol));
    }

}
