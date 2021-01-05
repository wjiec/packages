package com.wjiec.springaio.shop.domain;

import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.jadira.usertype.moneyandcurrency.joda.AbstractSingleColumnMoneyUserType;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spring_item")
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {

    private String title;

    /**
     * {@link AbstractSingleColumnMoneyUserType#applyConfiguration}
     */
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount", parameters = {
        @Parameter(name = "currencyCode", value = "CNY")
    })
    private Money price;

}
