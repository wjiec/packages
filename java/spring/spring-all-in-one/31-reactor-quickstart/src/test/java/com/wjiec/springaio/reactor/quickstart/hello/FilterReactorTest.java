package com.wjiec.springaio.reactor.quickstart.hello;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class FilterReactorTest {

    @Test
    public void skip() {
        var number = Flux.range(1, 5).skip(3);

        StepVerifier.create(number)
            .expectNext(4)
            .expectNext(5)
            .verifyComplete();
    }

    @Test
    public void skipDuration() {
        var number = Flux.range(1, 5)
            .delayElements(Duration.ofSeconds(1))
            .skip(Duration.ofSeconds(3))
            .delaySubscription(Duration.ofSeconds(10));

        StepVerifier.create(number)
            .expectNext(3)
            .expectNext(4)
            .expectNext(5)
            .verifyComplete();
    }

    @Test
    public void take() {
        var number = Flux.range(1, 5).take(3);

        StepVerifier.create(number)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .verifyComplete();
    }

    @Test
    public void takeDuration() {
        var number = Flux.range(1, 5)
            .delayElements(Duration.ofSeconds(1))
            .take(Duration.ofSeconds(3));

        StepVerifier.create(number)
            .expectNext(1)
            .expectNext(2)
            .verifyComplete();
    }

    @Test
    public void filter() {
        var number = Flux.range(1, 5)
            .filter(n -> n % 2 == 0);

        StepVerifier.create(number)
            .expectNext(2)
            .expectNext(4)
            .verifyComplete();
    }

    @Test
    public void distinct() {
        var number = Flux.generate((g) -> g.next(ThreadLocalRandom.current().nextInt(5)))
            .take(1024)
            .distinct()
            .sort();

        StepVerifier.create(number)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .verifyComplete();
    }

    @Test
    public void map() {
        var number = Flux.range(1, 5)
            .map(n -> n * n);

        StepVerifier.create(number)
            .expectNext(1)
            .expectNext(4)
            .expectNext(9)
            .expectNext(16)
            .expectNext(25)
            .verifyComplete();
    }

    @Test
    public void flatMap() {
        Flux.range(1, 5)
            .flatMap(n -> Flux.range(0, n)
                .map(v -> v * v)
                .subscribeOn(Schedulers.parallel()))
            .filter(n -> n % 2 == 0)
            .subscribe(System.out::println);
    }

    @Test
    public void buffer() {
        Flux.range(1, 1000)
            .buffer(10)
            .flatMap(it -> Flux.fromIterable(it)
                .collectList()
                .map(ns -> ns.stream().mapToInt(Integer::intValue).sum())
                .subscribeOn(Schedulers.parallel())
                .log())
            .subscribe(System.out::println);
    }

    @Test
    public void all() {
        Flux.range(1, 5)
            .all(n -> n - 5 < 0)
            .subscribe(System.out::println);
    }

    @Test
    public void any() {
        Flux.range(1, 5)
            .any(n -> n - 5 < 0)
            .subscribe(System.out::println);
    }

}
