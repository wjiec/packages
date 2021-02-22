package com.wjiec.springaio.artemis.controller;

import com.wjiec.springaio.artemis.domain.Message;
import com.wjiec.springaio.artemis.support.jms.annotation.CliDestination;
import com.wjiec.springaio.artemis.support.jms.annotation.WebDestination;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final Destination web;
    private final Destination cli;

    public MessageController(@WebDestination Destination web, @CliDestination Destination cli) {
        this.web = web;
        this.cli = cli;
    }

    @GetMapping("/web")
    public Message receiveFromWeb(JmsTemplate jmsTemplate) {
        return (Message) jmsTemplate.receiveAndConvert(web);
    }

    @GetMapping("/cli")
    public Message receiveFromCli(JmsTemplate jmsTemplate) {
        return (Message) jmsTemplate.receiveAndConvert(cli);
    }

}
