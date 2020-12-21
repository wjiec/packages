package com.wjiec.springaio.orm.repository;

import com.wjiec.springaio.orm.model.Administrator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Repository
@Transactional
public class JpaRepository {

    private final EntityManagerFactory entityManagerFactory;

    public JpaRepository(LocalContainerEntityManagerFactoryBean factoryBean) {
        entityManagerFactory = factoryBean.getObject();
    }

    public void addAdministrator(Administrator administrator) {
        entityManagerFactory.createEntityManager().persist(administrator);
    }

    public Administrator findById(long id) {
        return entityManagerFactory.createEntityManager().find(Administrator.class, id);
    }

}
