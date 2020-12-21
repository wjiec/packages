package com.wjiec.springaio.orm.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AdministratorRepositoryImpl implements JpaChecker {

//    @PersistenceContext
    private EntityManager entityManager;

    public void check() {
        System.out.println(entityManager);
    }

}
