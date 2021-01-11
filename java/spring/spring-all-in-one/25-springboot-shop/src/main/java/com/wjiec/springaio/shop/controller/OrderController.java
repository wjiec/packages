package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.domain.Item;
import com.wjiec.springaio.shop.domain.Order;
import com.wjiec.springaio.shop.domain.OrderItem;
import com.wjiec.springaio.shop.repository.ItemRepository;
import com.wjiec.springaio.shop.repository.OrderItemRepository;
import com.wjiec.springaio.shop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Controller;
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
@SessionAttributes({"cart", "order"})
public class OrderController {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderController(ItemRepository itemRepository, OrderRepository orderRepository,
                           OrderItemRepository orderItemRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping("/confirm")
    public String confirmOrder(@ModelAttribute Cart cart, @ModelAttribute Order order,
                               @ModelAttribute("items") List<Item> items) {
        if (cart == null || cart.getItemIds() == null || cart.getItemIds().size() == 0) {
            return "redirect:/shopping";
        }

        order.setItems(items);

        return "order/confirm";
    }

    @Transactional
    @PostMapping("/confirm")
    public String createOrder(@Validated Order order, Errors errors, @ModelAttribute("finalPrice") Money finalPrice,
                              SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "order/confirm";
        }

        order.setFinalPrice(finalPrice);
        orderRepository.save(order);
        for (var item : order.getItems()) {
            orderItemRepository.save(OrderItem.builder().itemId(item.getId()).orderId(order.getId()).build());
        }

        sessionStatus.setComplete();
        log.info("create order: {}", order);

        return "redirect:/";
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    public String sessionMissing(HttpSessionRequiredException ex) {
        log.info(ex.getMessage());
        return "redirect:/shopping";
    }

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute("items")
    public List<Item> items(@ModelAttribute Cart cart) {
        if (cart.getItemIds() != null && cart.getItemIds().size() != 0) {
            return itemRepository.findAllById(cart.getItemIds());
        }

        return List.of();
    }

    @ModelAttribute("finalPrice")
    public Money finalPrice(@ModelAttribute("items") List<Item> items) {
        return items.stream().reduce(Money.of(CurrencyUnit.of("CNY"), 0),
            ((Money money, Item item) -> money.plus(item.getPrice())), (l, r) -> l);
    }

}
