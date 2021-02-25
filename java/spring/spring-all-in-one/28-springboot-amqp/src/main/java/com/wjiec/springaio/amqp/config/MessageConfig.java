package com.wjiec.springaio.amqp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue reviewQueue() {
        return new Queue("spring.order.review", true);
    }

    @Bean
    public Queue saverQueue() {
        return new Queue("spring.order.saver", true);
    }

}
