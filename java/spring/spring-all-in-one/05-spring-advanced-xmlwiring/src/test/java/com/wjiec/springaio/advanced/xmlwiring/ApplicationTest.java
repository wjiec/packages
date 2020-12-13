package com.wjiec.springaio.advanced.xmlwiring;

import com.wjiec.springaio.advanced.xmlwiring.database.Database;
import com.wjiec.springaio.advanced.xmlwiring.database.Postgresql;
import com.wjiec.springaio.advanced.xmlwiring.model.Notepad;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/beans.xml")
@ActiveProfiles("prod")
class ApplicationTest {

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoaded() {}

    private final Set<Notepad> notepads = new HashSet<>();

    @RepeatedTest(100)
    void notepadsMustBeUnique() {
        Notepad notepad = context.getBean(Notepad.class);

        assertThat(notepads, not(hasItem(notepad)));
        notepads.add(notepad);
    }

    @Autowired
    private Database database;

    @Test
    void primaryDatabaseMustBePgSql() {
        assertNotNull(database);
        assertEquals(Postgresql.class, database.getClass());
    }

}
