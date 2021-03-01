package com.wjiec.springaio.integration.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "loggingChannel")
public interface LoggingGateway {

    void log(@Header(value = "X-Logging-Level") Level level, String message);

    enum Level {
        CRITICAL, ERROR, WARNING, INFO, DEBUG
    }

}
