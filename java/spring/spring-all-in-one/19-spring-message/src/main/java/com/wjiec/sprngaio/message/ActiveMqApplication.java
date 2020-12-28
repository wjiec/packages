package com.wjiec.sprngaio.message;

import com.wjiec.sprngaio.message.service.AlertService;
import com.wjiec.sprngaio.message.service.AlertServiceImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.HashMap;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.wjiec.sprngaio.message.service")
public class ActiveMqApplication {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ActiveMqApplication.class);
        context.refresh();

        ActiveMQQueue queue = context.getBean(ActiveMQQueue.class);
        System.out.println(queue);

        ActiveMQTopic topic = context.getBean(ActiveMQTopic.class);
        System.out.println(topic);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        System.out.println(jmsTemplate);

        new Thread(() -> {
            Object message = jmsTemplate.receiveAndConvert();
            System.out.println(message);
        }).start();

        AlertService alertService = context.getBean(AlertService.class);
        System.out.println(alertService);

        alertService.alert("title message", "content message");
        alertService.alert("title message", "content message");
        alertService.alert("title message", "content message");

        Thread.sleep(1000);
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        return connectionFactory;
    }

    @Bean
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("spring.queue");
    }

    @Bean
    public ActiveMQTopic topic() {
        return new ActiveMQTopic("spring.topic");
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdMappings(new HashMap<>() {{
            put("JMSType", AlertServiceImpl.Message.class);
        }});
        converter.setTypeIdPropertyName("JMSType");

        return converter;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(AlertHandler handler) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(activeMQConnectionFactory());
        container.setDestination(topic());
        container.setupMessageListener(handler);

        return container;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
        jmsTemplate.setDefaultDestination(topic());
        jmsTemplate.setMessageConverter(messageConverter());

        return jmsTemplate;
    }

    @Bean
    public JmsInvokerServiceExporter jmsInvokerServiceExporter(AlertService alertService) {
        JmsInvokerServiceExporter serviceExporter = new JmsInvokerServiceExporter();
        serviceExporter.setService(alertService);
        serviceExporter.setServiceInterface(AlertService.class);

        return serviceExporter;
    }

    @Bean
    public JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean() {
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        factoryBean.setConnectionFactory(activeMQConnectionFactory());
        factoryBean.setQueue(queue());
        factoryBean.setServiceInterface(AlertService.class);

        return factoryBean;
    }

    @Component
    public static class AlertHandler implements MessageListener {
        @Override
        public void onMessage(Message message) {
            System.out.println("AlertHandler.handle : " + message);
        }
    }

}
