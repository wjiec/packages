package com.wjiec.springaio.artemis.task;

import com.wjiec.springaio.artemis.domain.Administrator;
import com.wjiec.springaio.artemis.support.jms.annotation.CliDestination;
import com.wjiec.springaio.artemis.support.jms.annotation.WebDestination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Scanner;

@Slf4j
@Component
public class CliScanner implements CommandLineRunner {

    private final Destination web;
    private final Destination cli;
    private final JmsTemplate jmsTemplate;

    public CliScanner(@WebDestination Destination web, @CliDestination Destination cli,
                             JmsTemplate jmsTemplate) {
        this.web = web;
        this.cli = cli;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                Administrator administrator = Administrator.builder()
                    .nickname(scanner.next())
                    .password(scanner.next())
                    .build();
                jmsTemplate.convertAndSend(web, administrator, message -> {
                    message.setStringProperty("source", "cli");

                    return message;
                });

                jmsTemplate.convertAndSend(cli, administrator, message -> {
                    message.setStringProperty("source", "cli");

                    return message;
                });

                log.info("send administrator: {}", administrator);
            }
        }
    }

}
