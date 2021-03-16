package com.wjiec.springaio.cloud.config.userservice.controller;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final ConfigurableEnvironment environment;

    public ConfigController(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @GetMapping
    public Object configs(@RequestParam String key) {
        Map<String, Object> data = new HashMap<>();
        data.put(key, environment.getProperty(key));
        return data;
    }

}
