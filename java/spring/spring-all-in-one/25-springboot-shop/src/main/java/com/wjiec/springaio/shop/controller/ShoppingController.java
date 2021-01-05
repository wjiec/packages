package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String getItems(Model model, @ModelAttribute Cart cart) {
        model.addAttribute("cart", cart);
        model.addAttribute("items", itemRepository.findAll());

        return "shopping/items";
    }

    @PostMapping
    public String createCart(@ModelAttribute @Validated Cart cart, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("items", itemRepository.findAll());

            return "shopping/items";
        }

        log.info("create object: {}", cart);
        return "redirect:/order/confirm";
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

}
