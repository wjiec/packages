package com.wjiec.springaio.kafka.task;

import com.wjiec.springaio.kafka.domain.Coordinate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoordinateLogger {

    @KafkaListener(topics = "spring-topic")
    public void handle(Coordinate coordinate) {
        log.info("receive coordinate: {}", coordinate);
    }

}
