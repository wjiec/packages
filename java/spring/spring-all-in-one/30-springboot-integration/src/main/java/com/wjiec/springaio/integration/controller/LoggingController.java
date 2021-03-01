package com.wjiec.springaio.integration.controller;

import com.wjiec.springaio.integration.gateway.LoggingGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logging")
public class LoggingController {

    private final LoggingGateway loggingGateway;

    public LoggingController(LoggingGateway loggingGateway) {
        this.loggingGateway = loggingGateway;
    }

    @PostMapping("/{level}")
    public String log(@PathVariable String level, @RequestBody String message) {
        try {
            LoggingGateway.Level value = LoggingGateway.Level.valueOf(level.toUpperCase());
            loggingGateway.log(value, message.trim());

            return "OK";
        } catch (IllegalArgumentException ignored) {}

        return "ERROR";
    }

}
