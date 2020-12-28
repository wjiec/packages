package com.wjiec.springaio.stomp.controller;

import com.wjiec.springaio.stomp.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MessageController {

    @MessageMapping("/log")
    public void log(Message message) {
        log.info("log : " + message.toString());
    }

    @SubscribeMapping("/reverse")
    public Message reverse(Message message) {
        log.info("reverse : " + message.toString());

        return Message.builder().content(new StringBuffer(message.getContent()).reverse().toString()).build();
    }

}
