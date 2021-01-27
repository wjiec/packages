package com.wjiec.springaio.shop.controller.api;

import com.wjiec.springaio.shop.domain.Order;
import com.wjiec.springaio.shop.repository.OrderRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/order", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ApiOrderController {

    private final OrderRepository orderRepository;

    public ApiOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> orders() {
        return orderRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Order replaceOrder(@PathVariable long orderId, @RequestBody Order order) {
        Optional<Order> saved = orderRepository.findById(orderId);
        saved.ifPresent(value -> order.setId(value.getId()));

        return orderRepository.save(order);
    }

    @PatchMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> patchOrder(@PathVariable long orderId, @RequestBody Order order) {
        Optional<Order> saved = orderRepository.findById(orderId);
        saved.ifPresent(value -> {
            if (order.getAddress() != null) {
                value.setAddress(order.getAddress());
            }

            if (order.getCcNumber() != null) {
                value.setCcNumber(order.getCcNumber());
            }

            if (order.getCcCvv() != null) {
                value.setCcCvv(order.getCcCvv());
            }
        });

        return saved.map(value -> ResponseEntity.ok(orderRepository.save(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{orderId}")
    public void removeOrder(@PathVariable long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {}
    }

}
