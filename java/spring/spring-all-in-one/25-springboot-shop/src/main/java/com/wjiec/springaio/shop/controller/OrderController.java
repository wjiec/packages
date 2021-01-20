package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.config.ShopProperties;
import com.wjiec.springaio.shop.domain.Cart;
import com.wjiec.springaio.shop.domain.Item;
import com.wjiec.springaio.shop.domain.Order;
import com.wjiec.springaio.shop.domain.OrderItem;
import com.wjiec.springaio.shop.repository.ItemRepository;
import com.wjiec.springaio.shop.repository.OrderItemRepository;
import com.wjiec.springaio.shop.repository.OrderRepository;
import com.wjiec.springaio.shop.type.Session;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping
    public String orderList(Model model, ShopProperties properties, Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        model.addAttribute("orders", orderRepository.findByUserIdOrderByCreatedAtDesc(
            ((Session) authentication.getDetails()).getUser().getId(),
            PageRequest.of(0, properties.getPagination().getSize())
        ));

        return "order/list";
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
    public String createOrder(@Validated Order order, Errors errors,
                              @ModelAttribute("finalPrice") Money finalPrice,
                              SessionStatus sessionStatus, @AuthenticationPrincipal Session session) {
        if (errors.hasErrors()) {
            return "order/confirm";
        }

        order.setUserId(session.getUser().getId());
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
    public Order order(@AuthenticationPrincipal Session session) {
        return Order.builder().name(session.getUsername()).build();
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
