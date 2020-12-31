package com.wjiec.springaio.shop.domain;

import lombok.Builder;
import lombok.Data;
import org.joda.money.Money;

@Data
@Builder
public class Item {

    private Long id;
    private String title;
    private Money price;

}
