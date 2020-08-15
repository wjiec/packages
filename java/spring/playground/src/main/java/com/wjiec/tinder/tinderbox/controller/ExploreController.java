package com.wjiec.tinder.tinderbox.controller;

import com.wjiec.tinder.tinderbox.bean.BlogConfigurator;
import com.wjiec.tinder.tinderbox.config.BlogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ExploreController {

    private final BlogProperties blogProperties;

    private final BlogConfigurator blogConfigurator;

    @Autowired
    public ExploreController(BlogProperties properties, BlogConfigurator configurator) {
        this.blogProperties = properties;
        this.blogConfigurator = configurator;
    }

    @RequestMapping("/")
    public Map<String, Object> index() {
        return new HashMap<>() {{
            put("name", blogProperties.getName());
            put("title", blogConfigurator.getTitle());
            put("keywords", blogProperties.getKeywords());
            put("description", blogConfigurator.getDescription());
        }};
    }

}
