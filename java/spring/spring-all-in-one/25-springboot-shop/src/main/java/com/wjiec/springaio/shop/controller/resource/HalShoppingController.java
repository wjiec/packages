package com.wjiec.springaio.shop.controller.resource;

import com.wjiec.springaio.shop.controller.ShoppingController;
import com.wjiec.springaio.shop.repository.ItemRepository;
import com.wjiec.springaio.shop.resource.ItemResource;
import com.wjiec.springaio.shop.resource.ItemResourceAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.core.WebHandler.linkTo;

@RestController
@RequestMapping("/resource/shopping")
public class HalShoppingController {

    private final ItemRepository itemRepository;

    public HalShoppingController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public CollectionModel<ItemResource> items() {
        return new ItemResourceAssembler().toCollectionModel(itemRepository.findAll());
    }

}
