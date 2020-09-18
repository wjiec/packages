package com.wjiec.tinder.springinaction.spittr.web.controller;

import com.wjiec.tinder.springinaction.spittr.repository.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SpittleController {

    SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository repository) {
        this.spittleRepository = repository;
    }

    @RequestMapping(value = "/spittles", method = RequestMethod.GET)
    public String spittles(Model model) {
        model.addAttribute(spittleRepository.findSpittles(20L, 0L));
        return "spittles";
    }

}
