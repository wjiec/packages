package com.wjiec.springaio.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Component
public class CpuIndicator implements HealthIndicator {

    @Override
    public Health health() {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
        if (bean.getSystemLoadAverage() > 0.5) {
            return Health.down().withDetail("cpu-load", bean.getSystemLoadAverage()).build();
        }
        return Health.up().withDetail("cpu-load", bean.getSystemLoadAverage()).build();
    }

}
