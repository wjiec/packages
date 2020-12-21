package com.wjiec.springaio.orm.repository;

import com.wjiec.springaio.orm.model.Administrator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class NoSpringRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public NoSpringRepository(LocalSessionFactoryBean factoryBean) {
        this.sessionFactory = factoryBean.getObject();
    }

    public Administrator save(Administrator administrator) {
        Serializable id = currentSession().save(administrator);

        return Administrator.builder()
            .id((Long) id)
            .username(administrator.getUsername())
            .password(administrator.getPassword())
            .createdAt(administrator.getCreatedAt())
            .updatedAt(administrator.getUpdatedAt())
            .build();
    }

    @SuppressWarnings("deprecated")
    public List<Administrator> findAll() {
        return (List<Administrator>) currentSession().createCriteria(Administrator.class).list();
    }

    Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

}
