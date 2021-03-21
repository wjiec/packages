package com.wjiec.springaio.jmx.notification.notification;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import java.util.concurrent.atomic.AtomicLong;

@Component
@ManagedResource
public class Counter implements NotificationPublisherAware {

    private final AtomicLong count = new AtomicLong(0L);
    private NotificationPublisher publisher;

    @Override
    public void setNotificationPublisher(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedRate = 1_000)
    public void incrementCount() {
        var value = count.incrementAndGet();
        if (value != 0 && value % 10 == 0) {
            publisher.sendNotification(new Notification("schedule.count",
                this, value, "schedule count: " + value));
        }
    }

    @ManagedAttribute
    public long getCount() {
        return count.get();
    }

    @ManagedOperation
    public long increment(long value) {
        return count.addAndGet(value);
    }

}
