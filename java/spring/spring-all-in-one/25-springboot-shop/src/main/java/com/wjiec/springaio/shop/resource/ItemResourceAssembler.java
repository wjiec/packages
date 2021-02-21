package com.wjiec.springaio.shop.resource;

import com.wjiec.springaio.shop.controller.resource.HalShoppingController;
import com.wjiec.springaio.shop.domain.Item;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class ItemResourceAssembler extends RepresentationModelAssemblerSupport<Item, ItemResource> {

    public ItemResourceAssembler() {
        super(HalShoppingController.class, ItemResource.class);
    }

    @Override
    public ItemResource toModel(Item entity) {
        return new ItemResource(entity);
    }

}
