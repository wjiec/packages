package com.wjiec.springaio.cloud.config.userservice.runner;

import com.wjiec.springaio.cloud.config.userservice.config.ServiceProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class Scheduler {

    private final ServiceProperties properties;

    public Scheduler(ServiceProperties properties) {
        this.properties = properties;
    }

    @Scheduled(fixedRate = 10_000)
    public void dumpProperties() {
        log.info("properties: {}", properties);
    }

}
