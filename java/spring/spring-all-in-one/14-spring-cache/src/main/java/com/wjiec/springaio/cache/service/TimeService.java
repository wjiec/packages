package com.wjiec.springaio.cache.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeService {

    @Cacheable(value = "cacheName", key = "#result.year")
    public LocalDateTime now() {
        System.out.println("call now");
        return LocalDateTime.now();
    }

    @CachePut(value = "cacheName", key = "#result.year", unless = "#result.second == 0", condition = "#result.second == 59")
    public LocalDateTime update() {
        System.out.println("call update");
        return LocalDateTime.now();
    }

    @CacheEvict(value = "cacheName", key = "2020")
    public void remove() {
        System.out.println("call remove");
    }

}
