package com.wjiec.springaio.web.repository;

import com.wjiec.springaio.web.dto.SpitterDTO;
import com.wjiec.springaio.web.model.Spitter;

public interface SpitterRepository {

    Spitter findOne(String username);

    Spitter save(SpitterDTO dto);

}
