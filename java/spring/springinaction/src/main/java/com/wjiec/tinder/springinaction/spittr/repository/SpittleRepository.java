package com.wjiec.tinder.springinaction.spittr.repository;

import com.wjiec.tinder.springinaction.spittr.model.Spittle;

import java.util.List;

public interface SpittleRepository {

    List<Spittle> findSpittles(long count, long skip);

    Spittle findOne(long id);

}
