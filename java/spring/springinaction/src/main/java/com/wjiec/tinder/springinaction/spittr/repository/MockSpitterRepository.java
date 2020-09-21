package com.wjiec.tinder.springinaction.spittr.repository;

import com.wjiec.tinder.springinaction.spittr.model.Spitter;
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
    public boolean save(Spitter spitter) {
        if (!spitters.containsKey(spitter.getUsername())) {
            spitter.setId(ai.incrementAndGet());
            spitter.setCreatedAt(new Date());
            spitters.put(spitter.getUsername(), spitter);
            return true;
        }
        return false;
    }

}
