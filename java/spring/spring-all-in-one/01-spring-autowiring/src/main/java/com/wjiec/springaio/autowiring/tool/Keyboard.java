package com.wjiec.springaio.autowiring.tool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("keyboard")
public class Keyboard {

    private String name;

}
