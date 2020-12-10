package com.wjiec.springaio.autowiring.tool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("casio")
@ConfigurationProperties("calculator")
public class Calculator {

    private String name;

}
