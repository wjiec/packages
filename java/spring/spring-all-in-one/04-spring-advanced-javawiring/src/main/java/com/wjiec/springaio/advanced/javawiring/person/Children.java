package com.wjiec.springaio.advanced.javawiring.person;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("children")
public class Children implements Person {

    private final String name;
    private final int age;

    @Autowired
    public Children(@Value("${children.name}") String name, @Value("${children.age}") int age) {
        this.name = name;
        this.age = age;
    }

}
