package com.wjiec.springaio.traveron.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Accessor implements CommandLineRunner {

    private final Traverson traverson;

    public Accessor(Traverson traverson) {
        this.traverson = traverson;
    }

    @Override
    public void run(String... args) {
        Map<?, ?> data = traverson.follow("items")
            .toObject(Map.class);
        System.out.println(data);

        System.out.println(traverson.follow("items").asLink());
    }

}
