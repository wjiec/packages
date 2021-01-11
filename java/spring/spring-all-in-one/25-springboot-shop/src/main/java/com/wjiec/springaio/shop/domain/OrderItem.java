package com.wjiec.springaio.shop.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spring_order_item")
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {

    @Column(name = "item_id", updatable = false)
    private Long itemId;

    @Column(name = "order_id", updatable = false)
    private Long orderId;

}
