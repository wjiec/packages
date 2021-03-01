package com.wjiec.springaio.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class LoggingIntegrationConfig {

    @Bean
    @Qualifier("loggingChannel")
    public MessageChannel loggingChannel() {
        return new PublishSubscribeChannel(integrationExecutor());
    }

    @Bean
    public Executor integrationExecutor() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

}
