package com.wjiec.springaio.reactor.quickstart.hello;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CreateReactorTest {

    @Test
    public void createFromObject() {
        StepVerifier.create(Flux.just("hello", "world", "from", "object"))
            .expectNext("hello")
            .expectNext("world")
            .expectNext("from")
            .expectNext("object")
            .verifyComplete();

    }

    @Test
    public void createFromArray() {
        StepVerifier.create(Flux.fromArray(new String[]{"hello", "world", "from", "array"}))
            .expectNext("hello")
            .expectNext("world")
            .expectNext("from")
            .expectNext("array")
            .verifyComplete();
    }

    @Test
    public void createFromIterator() {
        List<String> values = new ArrayList<>();
        values.add("hello");
        values.add("world");
        values.add("from");
        values.add("iterator");

        StepVerifier.create(Flux.fromIterable(values))
            .expectNext("hello")
            .expectNext("world")
            .expectNext("from")
            .expectNext("iterator")
            .verifyComplete();
    }

    @Test
    public void createFromStream() {
        StepVerifier.create(Flux.fromStream(Stream.of("hello", "world", "from", "array")))
            .expectNext("hello")
            .expectNext("world")
            .expectNext("from")
            .expectNext("array")
            .verifyComplete();
    }

    @Test
    public void createFromRange() {
        StepVerifier.create(Flux.range(1, 5))
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .expectNext(5)
            .verifyComplete();
    }

}
