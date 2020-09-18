package com.wjiec.tinder.springinaction.spittr.repository;

import com.wjiec.tinder.springinaction.spittr.model.Spittle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpittleRepository {

    List<Spittle> findSpittles(long count, long skip);

}
