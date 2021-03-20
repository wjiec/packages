package com.wjiec.springaio.cloud.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/{name}")
    public String item(@PathVariable String name) {
        return name;
    }

    @GetMapping("/{name}/price")
    public Integer price(@PathVariable String name) {
        return name.hashCode();
    }

}
