package com.wjiec.springaio.reactor.quickstart.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class HelloWorld implements CommandLineRunner {

    @Override
    public void run(String... args) {
        Mono.just(HelloWorld.class.getName())
            .map(String::toUpperCase)
            .map(s -> "Class " + s)
            .subscribe(log::info);

        Flux.just(args)
            .map(String::toCharArray)
            .map(cs -> String.format("%s -> %d", Arrays.toString(cs), cs.length))
            .subscribe(log::info);
    }

}
