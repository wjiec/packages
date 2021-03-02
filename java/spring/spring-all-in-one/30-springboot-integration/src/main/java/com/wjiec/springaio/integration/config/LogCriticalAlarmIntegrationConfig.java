package com.wjiec.springaio.integration.config;

import com.wjiec.springaio.integration.gateway.LoggingGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LogCriticalAlarmIntegrationConfig {

    @Value("${alarm.receiver}")
    private String alarmReceiver;

    @Value("${alarm.sender}")
    private String alarmSender;

    @Bean
    public IntegrationFlow logCriticalAlarm(@Qualifier("loggingChannel") MessageChannel loggingChannel,
                                            JavaMailSender mailSender) {
        return IntegrationFlows
            .from(loggingChannel)
            .filter(Message.class, m -> m.getHeaders().get("X-Logging-Level") == LoggingGateway.Level.CRITICAL)
            .transform(Message.class, message -> {
                Map<String, Object> headers = new HashMap<>(message.getHeaders());
                headers.put(MailHeaders.SUBJECT, "Spring Logging Alarm");
                headers.put(MailHeaders.TO, alarmReceiver);
                headers.put(MailHeaders.FROM, alarmSender);

                return MessageBuilder.createMessage(message.getPayload(), new MessageHeaders(headers));
            })
            .handle(new MailSendingMessageHandler(mailSender))
            .get();
    }

}
