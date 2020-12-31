package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/confirm")
    public String confirmOrder(Model model) {
        model.addAttribute("order", new Order());

        return "order/confirm";
    }

    @PostMapping("/confirm")
    public String createOrder(@Validated Order order, Errors errors) {
        if (errors.hasErrors()) {
            return "order/confirm";
        }

        log.info("create order: {}", order);
        return "redirect:/";
    }

}
