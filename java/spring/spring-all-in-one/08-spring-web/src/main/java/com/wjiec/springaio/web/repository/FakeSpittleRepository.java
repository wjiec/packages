package com.wjiec.springaio.web.repository;

import com.wjiec.springaio.web.model.Spittle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class FakeSpittleRepository implements SpittleRepository {

    @Override
    public List<Spittle> findSpittles(long count, long skip) {
        List<Spittle> spittles = new ArrayList<>();
        for (long i = skip; i < skip + count; i++) {
            spittles.add(Spittle.builder()
                .id(i)
                .content("Spittle-" + i)
                .createdAt(new Date())
                .latitude(Math.random())
                .longitude(Math.random())
                .build());
        }
        return spittles;
    }

    @Override
    public Spittle findOne(long id) {
        return Spittle.builder()
            .id(id)
            .content("Spittle-" + id)
            .createdAt(new Date())
            .latitude(Math.random())
            .longitude(Math.random())
            .build();
    }

}
