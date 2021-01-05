package com.wjiec.springaio.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spring_order")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @NotEmpty(message = "the name must not be empty")
    private String name;

    @NotEmpty(message = "the address must not be empty")
    private String address;

    @Column(name = "cc_number")
    @CreditCardNumber(message = "the credit card number invalid")
    private String ccNumber;

    @Column(name = "cc_cvv")
    @Digits(fraction = 3, message = "invalid cvv number", integer = 0)
    private String ccCvv;

    @Transient
    private List<Item> items;

}
