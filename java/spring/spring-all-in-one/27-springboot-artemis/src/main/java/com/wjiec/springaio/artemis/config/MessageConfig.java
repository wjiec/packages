package com.wjiec.springaio.artemis.config;

import com.wjiec.springaio.artemis.support.jms.annotation.CliDestination;
import com.wjiec.springaio.artemis.support.jms.annotation.WebDestination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;

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

}
