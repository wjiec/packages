package com.wjiec.springaio.stomp.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import java.util.UUID;

@Controller
public class HomeController {

    private final SimpMessageSendingOperations sendingOperations;

    public HomeController(SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @ResponseBody
    @RequestMapping("/home")
    public String home() {
        String message = UUID.randomUUID().toString();
        sendingOperations.convertAndSend("/topic/notify", message);

        return message;
    }

}
