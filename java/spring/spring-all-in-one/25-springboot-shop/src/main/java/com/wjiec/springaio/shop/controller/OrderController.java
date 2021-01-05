package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.domain.Item;
import com.wjiec.springaio.shop.domain.Order;
import com.wjiec.springaio.shop.repository.ItemRepository;
import com.wjiec.springaio.shop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes("cart")
public class OrderController {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public OrderController(ItemRepository itemRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/confirm")
    public String confirmOrder(Model model, @ModelAttribute Cart cart, @ModelAttribute Order order) {
        if (cart == null || cart.getItemIds() == null || cart.getItemIds().size() == 0) {
            return "redirect:/shopping";
        }

        List<Item> items = itemRepository.findAllById(cart.getItemIds());
        model.addAttribute("items", items);
        model.addAttribute("order", order);
        Money finalPrice = items.stream().reduce(Money.of(CurrencyUnit.of("CNY"), 0),
            ((Money money, Item item) -> money.plus(item.getPrice())), (l, r) -> l);
        model.addAttribute("finalPrice", finalPrice);
        order.setItems(items);

        return "order/confirm";
    }

    @Transactional
    @PostMapping("/confirm")
    public String createOrder(@Validated Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "order/confirm";
        }

        log.info("create order: {}", order);
        orderRepository.save(order);
        for (var item : order.getItems()) {
        }

        sessionStatus.setComplete();

        return "redirect:/";
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    public String sessionMissing() {
        return "redirect:/shopping";
    }

    @ModelAttribute
    public Order order() {
        return new Order();
    }

}
