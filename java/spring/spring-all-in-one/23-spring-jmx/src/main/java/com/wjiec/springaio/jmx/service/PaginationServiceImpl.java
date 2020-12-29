package com.wjiec.springaio.jmx.service;

import lombok.Data;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

import javax.management.Notification;

@Data
@Service
@ManagedResource
public class PaginationServiceImpl implements PaginationService, NotificationPublisherAware {

    private static final int DEFAULT_PAGINATION_INDEX = 1;
    private static final int DEFAULT_PAGINATION_SIZE = 20;

    private int paginationSize;
    private int paginationIndex;
    private NotificationPublisher notificationPublisher;

    public PaginationServiceImpl() {
        paginationIndex = DEFAULT_PAGINATION_INDEX;
        paginationSize = DEFAULT_PAGINATION_SIZE;
    }

    @ManagedOperation
    public int getPaginationSize() {
        return paginationSize;
    }

    public void setPaginationSize(int paginationSize) {
        this.paginationSize = paginationSize;
        notificationPublisher.sendNotification(new Notification(
            "setPaginationSize: " + paginationSize, this, 0));
    }

    public int getPaginationIndex() {
        return paginationIndex;
    }

    @ManagedAttribute
    public void setPaginationIndex(int paginationIndex) {
        this.paginationIndex = paginationIndex;
        notificationPublisher.sendNotification(new Notification(
            "setPaginationIndex: " + paginationIndex, this, 0));
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

}
