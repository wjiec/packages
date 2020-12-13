package com.wjiec.springaio.advanced.javawiring;

import com.wjiec.springaio.advanced.javawiring.person.Father;
import com.wjiec.springaio.advanced.javawiring.person.Person;
import com.wjiec.springaio.advanced.javawiring.shopping.Supermarket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("father")
class ApplicationTest {

    @Test
    void contextLoaded() {}

    @Autowired
    private Person person;

    @Test
    void personMustBeFather() {
        assertNotNull(person);
        assertEquals(Father.class, person.getClass());
    }

    @Value("${father.name}")
    private String fatherName;

    @Value("${father.age}")
    private int fatherAge;

    @Test
    void personPropertiesMustBeEquals() {
        assertEquals(fatherName, person.getName());
        assertEquals(fatherAge, person.getAge());
    }

    @Autowired
    ApplicationContext context;

    @Test
    void skirtMustBeNotExists() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(Supermarket.Skirt.class));
    }

}
