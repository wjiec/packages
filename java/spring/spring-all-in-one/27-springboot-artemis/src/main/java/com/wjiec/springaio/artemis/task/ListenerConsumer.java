package com.wjiec.springaio.artemis.task;

import com.wjiec.springaio.artemis.domain.Administrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerConsumer {

    @JmsListener(destination = "spring.cli")
    public void consume(Administrator administrator) {
        log.info("listener receive: {}", administrator);
    }

}
