package com.wjiec.springaio.web.repository;

import com.wjiec.springaio.web.model.Spittle;

import java.util.List;

public interface SpittleRepository {

    List<Spittle> findSpittles(long count, long skip);

    Spittle findOne(long id);

}
