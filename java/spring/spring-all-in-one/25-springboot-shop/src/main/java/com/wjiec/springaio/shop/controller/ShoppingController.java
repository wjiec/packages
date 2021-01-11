package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.domain.Item;
import com.wjiec.springaio.shop.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/shopping")
@SessionAttributes("cart")
public class ShoppingController {

    private final ItemRepository itemRepository;

    public ShoppingController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String getItems(@ModelAttribute Cart cart) {
        return "shopping/items";
    }

    @PostMapping
    public String createCart(@ModelAttribute @Validated Cart cart, Errors errors) {
        if (errors.hasErrors()) {
            return "shopping/items";
        }

        log.info("create object: {}", cart);
        return "redirect:/order/confirm";
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @ModelAttribute("items")
    public List<Item> items() {
        return itemRepository.findAll();
    }

}
