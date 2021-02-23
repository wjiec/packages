package com.wjiec.springaio.artemis.config;

import com.wjiec.springaio.artemis.domain.Administrator;
import com.wjiec.springaio.artemis.support.jms.annotation.CliDestination;
import com.wjiec.springaio.artemis.support.jms.annotation.WebDestination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageConfig {

    @Bean
    @WebDestination
    public Destination webDestination() {
        return new ActiveMQQueue("spring.web");
    }

    @Bean
    @CliDestination
    public Destination cliDestination() {
        return new ActiveMQQueue("spring.cli");
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("administrator", Administrator.class);
        messageConverter.setTypeIdMappings(mappings);

        return messageConverter;
    }

}
