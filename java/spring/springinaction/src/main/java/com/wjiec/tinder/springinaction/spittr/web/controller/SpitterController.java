package com.wjiec.tinder.springinaction.spittr.web.controller;

import com.wjiec.tinder.springinaction.spittr.model.Spitter;
import com.wjiec.tinder.springinaction.spittr.repository.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository repository) {
        this.spitterRepository = repository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm() {
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(Spitter spitter) {
        return "redirect:/spitter/" + spitter.getUsername();
    }

}
