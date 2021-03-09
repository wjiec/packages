package com.wjiec.springaio.webflux.controller;

import com.wjiec.springaio.webflux.model.Item;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping
    public Flux<Item> items() {
        return Flux.just(Item.builder().build(), Item.builder().build());
    }

    @PostMapping
    public Mono<Item> create(@RequestBody Mono<Item> item) {
        return item;
    }

}
