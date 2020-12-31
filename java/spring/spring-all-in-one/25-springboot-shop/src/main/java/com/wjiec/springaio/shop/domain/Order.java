package com.wjiec.springaio.shop.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@Data
public class Order {

    @NotEmpty(message = "the name must not be empty")
    private String name;

    @NotEmpty(message = "the address must not be empty")
    private String address;

    @CreditCardNumber(message = "the credit card number invalid")
    private String ccNumber;

    @Digits(fraction = 3, message = "invalid cvv number", integer = 0)
    private String ccCvv;

}
