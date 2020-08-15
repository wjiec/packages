package com.wjiec.tinder.tinderbox.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "tinder.blog")
public class BlogConfigurator {

    private String name;

    private String title;

    private List<String> keywords;

    private String description;

}
