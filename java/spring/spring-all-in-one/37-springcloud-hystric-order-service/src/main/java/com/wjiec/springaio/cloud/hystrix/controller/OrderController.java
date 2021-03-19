package com.wjiec.springaio.cloud.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import com.wjiec.springaio.cloud.hystrix.feign.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class OrderController {

    private final ItemService itemService;

    @GetMapping("/{name}")
    @HystrixCommand(fallbackMethod = "fallbackQueryItem", commandProperties = {
        @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "500")
    })
    public Map<String, String> queryItem(@PathVariable String name) {
        log.info("doing queryItem");
        return new HashMap<>() {{
            put(name, itemService.item("item." + name));
        }};
    }

    public Map<String, String> fallbackQueryItem(String name) {
        log.info("doing fallbackQueryItem");
        return Collections.emptyMap();
    }

}
