package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @GetMapping
    public String getItems(Model model) {
        model.addAttribute("cart", new Cart());
        model.addAttribute("items", getItems());

        return "shopping/items";
    }

    @PostMapping
    public String createCart(Model model, @Validated Cart cart, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("cart", new Cart());
            model.addAttribute("items", getItems());

            return "shopping/items";
        }

        log.info("create object: {}", cart);
        return "redirect:/order/confirm";
    }

    private List<Item> getItems() {
        return List.of(
            Item.builder().id(1L).title("Pen").price(Money.of(CurrencyUnit.of("CNY"), 2)).build(),
            Item.builder().id(1L).title("Clothes").price(Money.of(CurrencyUnit.of("CNY"), 500)).build(),
            Item.builder().id(1L).title("iPhone").price(Money.of(CurrencyUnit.of("CNY"), 6799)).build(),
            Item.builder().id(1L).title("Sony A7III").price(Money.of(CurrencyUnit.of("CNY"), 16999)).build()
        );
    }

}
