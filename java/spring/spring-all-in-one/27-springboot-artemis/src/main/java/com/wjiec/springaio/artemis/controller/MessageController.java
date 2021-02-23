package com.wjiec.springaio.artemis.controller;

import com.wjiec.springaio.artemis.domain.Administrator;
import com.wjiec.springaio.artemis.support.jms.annotation.CliDestination;
import com.wjiec.springaio.artemis.support.jms.annotation.WebDestination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final Destination web;
    private final Destination cli;
    private final JmsTemplate jmsTemplate;

    public MessageController(@WebDestination Destination web, @CliDestination Destination cli,
                             JmsTemplate jmsTemplate) {
        this.web = web;
        this.cli = cli;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping
    public Administrator receiveFromWeb() {
        return (Administrator) jmsTemplate.receiveAndConvert(web);
    }

    @PostMapping
    public Administrator createFromWeb(@RequestBody Administrator administrator) {
        log.info("receive administrator: {}", administrator);
        jmsTemplate.convertAndSend(web, administrator, (message) -> {
            message.setStringProperty("source", "web");

            return message;
        });

        jmsTemplate.convertAndSend(cli, administrator, (message) -> {
            message.setStringProperty("source", "web");

            return message;
        });

        return administrator;
    }

}
