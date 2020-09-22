package com.wjiec.tinder.springinaction.spittr.repository;

import com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO;
import com.wjiec.tinder.springinaction.spittr.model.Spitter;
import org.apache.taglibs.standard.extra.spath.SPathFilter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MockSpitterRepository implements SpitterRepository {

    private static final AtomicInteger ai = new AtomicInteger();
    private static final Map<String, Spitter> spitters = new ConcurrentHashMap<>();

    @Override
    public Spitter findOne(String username) {
        if (spitters.containsKey(username)) {
            return spitters.get(username);
        }
        return null;
    }

    @Override
    public Spitter save(SpitterDTO dto) {
        if (!spitters.containsKey(dto.getUsername())) {
            Spitter spitter = Spitter.builder()
                .id(ai.incrementAndGet())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .createdAt(new Date())
                .build();
            spitters.put(spitter.getUsername(), spitter);
            return spitter;
        }
        return spitters.get(dto.getUsername());
    }

}
