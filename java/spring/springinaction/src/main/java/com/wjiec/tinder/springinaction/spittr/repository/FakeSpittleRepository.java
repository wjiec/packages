package com.wjiec.tinder.springinaction.spittr.repository;

import com.wjiec.tinder.springinaction.spittr.model.Spittle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FakeSpittleRepository implements SpittleRepository {

    @Override
    public List<Spittle> findSpittles(long count, long skip) {
        List<Spittle> spittles = new ArrayList<>();
        for (long i = skip; i < skip + count; i++) {
            spittles.add(Spittle.builder()
                .id(i).name("Spittle-" + i)
                .createdAt(new Date())
                .latitude(Math.random())
                .longitude(Math.random())
                .build());
        }
        return spittles;
    }

}
