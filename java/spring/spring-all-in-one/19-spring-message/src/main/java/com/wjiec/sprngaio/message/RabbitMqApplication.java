package com.wjiec.sprngaio.message;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@Configuration
@ImportResource("/amqp.xml")
public class RabbitMqApplication {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RabbitMqApplication.class);
        context.refresh();

        RabbitOperations rabbitOperations = context.getBean(RabbitOperations.class);
        System.out.println(rabbitOperations);

        new Thread(() -> {
            while (true) {
//                Message message = rabbitOperations.receive("spring.queue");
//                System.out.println(message);
                String s = (String) rabbitOperations.receiveAndConvert("spring.queue");
                System.out.println(s);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }).start();

        Thread.sleep(1000);
        rabbitOperations.convertAndSend("spring.queue", "content");
        rabbitOperations.convertAndSend("spring.queue", "content");
        rabbitOperations.convertAndSend("spring.queue", "content");
        rabbitOperations.convertAndSend("spring.queue", "content");
        rabbitOperations.convertAndSend("spring.queue", "content");
        rabbitOperations.convertAndSend("spring.queue", "content");
        rabbitOperations.convertAndSend("spring.queue", "content");
    }

    @Bean
    public AmqpAdmin amqpAdmin(RabbitTemplate rabbitTemplate) {
        RabbitAdmin admin = new RabbitAdmin(rabbitTemplate);
        admin.declareQueue(new Queue("spring.queue"));

        return admin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Component("stringHandler")
    public static class StringHandler {
        public void handle(String message) {
            System.out.println("StringHandler.handle : " + message);
        }
    }

}
