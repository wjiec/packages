package com.wjiec.advanced.javawiring.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("mother")
@NoArgsConstructor
public class Mother implements Person {

    @Value("${mother.name}")
    private String name;

    @Value("${mother.age}")
    private int age;

}
