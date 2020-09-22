package com.wjiec.tinder.springinaction.spittr.repository;

import com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO;
import com.wjiec.tinder.springinaction.spittr.model.Spitter;

public interface SpitterRepository {

    Spitter findOne(String username);

    Spitter save(SpitterDTO dto);

}
