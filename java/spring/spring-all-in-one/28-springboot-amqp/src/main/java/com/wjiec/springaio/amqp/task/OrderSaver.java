package com.wjiec.springaio.amqp.task;

import com.wjiec.springaio.amqp.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderSaver {

    @RabbitListener(queues = "spring.order.saver")
    public void saveOrder(Order order) {
        log.info("save order: {}", order);
    }

}
