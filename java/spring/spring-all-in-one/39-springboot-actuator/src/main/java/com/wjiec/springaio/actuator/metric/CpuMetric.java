package com.wjiec.springaio.actuator.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@Component
public class CpuMetric {

    private final MeterRegistry registry;

    public CpuMetric(MeterRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(fixedRate = 1_000)
    public void cpuLoad() {
        registry.gauge("sys.cpu.load", ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
    }

}
