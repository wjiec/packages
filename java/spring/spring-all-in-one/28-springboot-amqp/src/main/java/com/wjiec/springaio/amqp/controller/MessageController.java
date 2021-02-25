package com.wjiec.springaio.amqp.controller;

import com.wjiec.springaio.amqp.domain.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final AmqpTemplate amqpTemplate;

    public MessageController(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @GetMapping
    public Order order() {
        return amqpTemplate.receiveAndConvert("spring.order.review",
            ParameterizedTypeReference.forType(Order.class));
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        amqpTemplate.convertAndSend("spring.order.saver", order);
        amqpTemplate.convertAndSend("spring.order.review", order, message -> {
            message.getMessageProperties().setHeader("X-Order-Source", "web");

            return message;
        });

        return order;
    }

}
