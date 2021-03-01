package com.wjiec.springaio.integration.config;

import com.wjiec.springaio.integration.gateway.LoggingGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@Configuration
public class TerminalPrinterIntegrationConfig {

    @Bean
    public IntegrationFlow terminalPrinter(@Qualifier("loggingChannel") MessageChannel loggingChannel) {
        return IntegrationFlows
            .from(loggingChannel)
            .handle(message -> {
                LoggingGateway.Level level = (LoggingGateway.Level) message.getHeaders().get("X-Logging-Level");
                System.out.printf("TERMINAL :: %-12s : %s\n", level, message.getPayload());
            })
            .get();
    }

}
