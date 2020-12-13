package com.wjiec.springaio.advanced.javawiring.person;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("father")
public class Father implements Person {

    @Value("${father.name}")
    private String name;

    @Value("${father.age}")
    private int age;

}
