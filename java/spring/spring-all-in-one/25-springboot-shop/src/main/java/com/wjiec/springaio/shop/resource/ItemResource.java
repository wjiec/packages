package com.wjiec.springaio.shop.resource;

import com.wjiec.springaio.shop.domain.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.money.Money;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(value = "item", collectionRelation = "items")
public class ItemResource extends RepresentationModel<ItemResource> {

    private long id;

    private String title;

    private Money price;

    public ItemResource(Item item) {
        id = item.getId();
        title = item.getTitle();
        price = item.getPrice();
    }

}
