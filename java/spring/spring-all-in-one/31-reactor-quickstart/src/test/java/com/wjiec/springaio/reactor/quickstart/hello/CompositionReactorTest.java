package com.wjiec.springaio.reactor.quickstart.hello;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class CompositionReactorTest {

    @Test
    public void merge() {
        var odd = Flux.just(1, 3, 5, 7, 9)
            .delaySubscription(Duration.ofMillis(100))
            .delayElements(Duration.ofMillis(200));
        var even = Flux.just(0, 2, 4, 6, 8)
            .delayElements(Duration.ofMillis(200));

        StepVerifier.create(odd.mergeWith(even))
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .expectNext(5)
            .expectNext(6)
            .expectNext(7)
            .expectNext(8)
            .expectNext(9)
            .verifyComplete();
    }

    @Test
    public void zip() {
        var number = Flux.just(1, 2, 3, 4, 5);
        var words = Flux.just("one", "two", "three", "four", "five");

        Flux<Tuple2<Integer, String>> zipped = Flux.zip(number, words);
        StepVerifier.create(zipped)
            .expectNextMatches((v) -> v.getT1() == 1 && v.getT2().equals("one"))
            .expectNextMatches((v) -> v.getT1() == 2 && v.getT2().equals("two"))
            .expectNextMatches((v) -> v.getT1() == 3 && v.getT2().equals("three"))
            .expectNextMatches((v) -> v.getT1() == 4 && v.getT2().equals("four"))
            .expectNextMatches((v) -> v.getT1() == 5 && v.getT2().equals("five"))
            .verifyComplete();
    }

    @Test
    public void zipWithCombinator() {
        var number = Flux.just(1, 2, 3, 4, 5);
        var words = Flux.just("one", "two", "three", "four", "five");

        Flux<String> zipped = Flux.zip(number, words, (n, w) -> String.format("%d:%s", n, w));
        StepVerifier.create(zipped)
            .expectNext("1:one")
            .expectNext("2:two")
            .expectNext("3:three")
            .expectNext("4:four")
            .expectNext("5:five")
            .verifyComplete();
    }

    @Test
    public void first() {
        var fruit = Flux.just("apple", "banana", "cherry");
        var number = Flux.just("one", "two", "three").delayElements(Duration.ofMillis(100));

        StepVerifier.create(Flux.firstWithSignal(fruit, number))
            .expectNext("apple")
            .expectNext("banana")
            .expectNext("cherry")
            .verifyComplete();
    }

}
