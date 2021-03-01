package com.wjiec.springaio.integration.config;

import com.wjiec.springaio.integration.gateway.LoggingGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LogFileWriterIntegrationConfig {

    private final DateTimeFormatter logFileNameFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");

    @Bean
    public IntegrationFlow logFileWriter(@Qualifier("loggingChannel") MessageChannel loggingChannel) {
        return IntegrationFlows
            .from(loggingChannel)
            .route("headers['X-Logging-Level']", mapping -> mapping
                .subFlowMapping(LoggingGateway.Level.DEBUG, subFlow -> subFlow
                    .handle(Files
                        .outboundAdapter(new File("/tmp/spring/debug"))
                        .fileNameGenerator(message -> LocalDateTime.now().format(logFileNameFormatter) + ".log")
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true)))
                .defaultSubFlowMapping(subFlow -> subFlow
                    .handle(Files
                        .outboundAdapter(new File("/tmp/spring/application"))
                        .fileNameGenerator(message -> LocalDateTime.now().format(logFileNameFormatter) + ".log")
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))))
            .get();
    }

    @Bean
    public MessageChannel debugLogChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel applicationLogChannel() {
        return new DirectChannel();
    }

}
