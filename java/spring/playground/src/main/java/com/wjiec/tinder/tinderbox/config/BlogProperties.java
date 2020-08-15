package com.wjiec.tinder.tinderbox.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BlogProperties {

    @Getter
    @Value("${tinder.blog.name}")
    private String name;

    @Getter
    @Value("${tinder.blog.title}")
    private String title;

    @Getter
    @Value("${tinder.blog.keywords}")
    private List<String> keywords;

    @Getter
    @Value("${tinder.blog.description}")
    private String description;

}
