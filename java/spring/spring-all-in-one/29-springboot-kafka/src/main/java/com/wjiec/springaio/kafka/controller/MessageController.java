package com.wjiec.springaio.kafka.controller;

import com.wjiec.springaio.kafka.domain.Coordinate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final KafkaTemplate<String, Coordinate> kafkaTemplate;

    public MessageController(KafkaTemplate<String, Coordinate> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public Coordinate createCoordinate(@RequestBody Coordinate coordinate) {
        kafkaTemplate.send("spring-topic", coordinate);

        return coordinate;
    }

}
