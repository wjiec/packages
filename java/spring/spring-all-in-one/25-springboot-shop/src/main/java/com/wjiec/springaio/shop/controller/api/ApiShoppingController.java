package com.wjiec.springaio.shop.controller.api;

import com.wjiec.springaio.shop.domain.Item;
import com.wjiec.springaio.shop.repository.ItemRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;


@CrossOrigin
@RestController
@RequestMapping(value = "/api/shopping", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
public class ApiShoppingController {

    private final ItemRepository itemRepository;

    public ApiShoppingController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Item> items() {
        return itemRepository.findAll();
    }

}
