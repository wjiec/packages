package com.wjiec.sprngaio.message.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@Primary
public class AlertServiceImpl implements AlertService {

    private final JmsOperations jmsOperations;

    @Autowired
    public AlertServiceImpl(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    @Override
    public void alert(String title, String content) {
        jmsOperations.convertAndSend(Message.builder()
            .title(title).content(content).build());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message implements Serializable {
        private String title;
        private String content;
    }

}
