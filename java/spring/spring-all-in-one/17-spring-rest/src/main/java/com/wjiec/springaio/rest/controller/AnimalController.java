package com.wjiec.springaio.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimalController {

    @GetMapping("/animal/{name}")
    public String kind(@PathVariable String name) {
        return name + ": I DON'T KNOWN";
    }

}
