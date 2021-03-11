package com.wjiec.springaio.webflux.controller;

import com.wjiec.springaio.webflux.model.Item;
import com.wjiec.springaio.webflux.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public Flux<Item> items() {
        return itemRepository.findAll().take(10);
    }

    @PostMapping
    public Mono<Item> create(@RequestBody Mono<Item> item) {
        return itemRepository.insert(item).next();
    }

}
