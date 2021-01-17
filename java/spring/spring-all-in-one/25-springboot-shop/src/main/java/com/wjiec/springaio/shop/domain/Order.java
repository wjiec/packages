package com.wjiec.springaio.shop.domain;

import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spring_order")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "the name must not be empty")
    private String name;

    @NotEmpty(message = "the address must not be empty")
    private String address;

    @Column(name = "cc_number")
//    @CreditCardNumber(message = "the credit card number invalid")
    private String ccNumber;

    @Column(name = "cc_cvv")
    @Digits(integer = 3, message = "invalid cvv number", fraction = 0)
    private String ccCvv;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount", parameters = {
        @Parameter(name = "currencyCode", value = "CNY")
    })
    @Column(name = "final_price")
    private Money finalPrice;

    @Transient
    private List<Item> items;

}
