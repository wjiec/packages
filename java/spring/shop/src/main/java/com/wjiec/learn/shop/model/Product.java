package com.wjiec.learn.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "product_title")
    private String title;

    @Column(name = "product_price")
    private Money price;

}
