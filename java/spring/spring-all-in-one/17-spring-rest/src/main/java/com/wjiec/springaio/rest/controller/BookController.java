package com.wjiec.springaio.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    @ResponseBody
    public List<String> books() {
        return List.of("spring in action", "golang in action", "c++ primer");
    }

    @RequestMapping("/book/{name}")
    @ResponseBody
    public String book(@PathVariable String name) {
        return name;
    }

    @ResponseBody
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public List<String> save(@RequestBody List<String> values) {
        return values.stream().map((s) -> s + " : " + s).collect(Collectors.toList());
    }

}
