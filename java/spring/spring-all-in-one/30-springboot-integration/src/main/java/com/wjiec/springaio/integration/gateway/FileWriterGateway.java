package com.wjiec.springaio.integration.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "loggingChannel")
public interface FileWriterGateway {

    void writeFile(@Header(FileHeaders.FILENAME) String filename, String payload);

}
