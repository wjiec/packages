package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.domain.Item;
import com.wjiec.springaio.shop.domain.Order;
import com.wjiec.springaio.shop.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes("cart")
public class OrderController {

    private final ItemRepository itemRepository;

    public OrderController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/confirm")
    public String confirmOrder(Model model, @ModelAttribute Cart cart) {
        if (cart == null || cart.getItemIds() == null || cart.getItemIds().size() == 0) {
            return "redirect:/shopping";
        }

        List<Item> items = itemRepository.findAllById(cart.getItemIds());
        model.addAttribute("items", items);
        model.addAttribute("order", new Order());

        return "order/confirm";
    }

    @PostMapping("/confirm")
    public String createOrder(@Validated Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "order/confirm";
        }

        log.info("create order: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    public String sessionMissing() {
        return "redirect:/shopping";
    }

}
