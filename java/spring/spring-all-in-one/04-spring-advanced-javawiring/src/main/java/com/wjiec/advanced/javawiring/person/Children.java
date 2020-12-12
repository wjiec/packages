package com.wjiec.advanced.javawiring.person;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("children")
public class Children implements Person {

    @Value("${children.name}")
    private String name;

    @Value("${children.age}")
    private int age;

}
