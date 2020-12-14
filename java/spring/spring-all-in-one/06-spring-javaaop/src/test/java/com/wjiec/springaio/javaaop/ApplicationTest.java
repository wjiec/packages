package com.wjiec.springaio.javaaop;

import com.wjiec.springaio.javaaop.database.Mysql;
import com.wjiec.springaio.javaaop.database.Postgresql;
import com.wjiec.springaio.javaaop.database.closer.Closer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
class ApplicationTest {

    @Test
    void contextLoaded() {}

    @Test
    void databaseMustBeProxy(ApplicationContext context) {
        assertNotNull(context);

        assertNotEquals(Mysql.class, context.getBean("mysql"));
        assertNotEquals(Postgresql.class, context.getBean("postgresql"));
    }

    @Test
    void databaseMustBeInheritCloser(ApplicationContext context) {
        assertNotNull(context);

        assertTrue(context.getBean("mysql") instanceof Closer);
        assertTrue(context.getBean("postgresql") instanceof Closer);
    }

}
