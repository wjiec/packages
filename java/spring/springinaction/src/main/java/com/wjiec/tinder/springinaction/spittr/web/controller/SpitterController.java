package com.wjiec.tinder.springinaction.spittr.web.controller;

import com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO;
import com.wjiec.tinder.springinaction.spittr.model.Spitter;
import com.wjiec.tinder.springinaction.spittr.repository.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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
    public String processRegistration(@Validated SpitterDTO spitterDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "registerForm";
        }

        Spitter spitter = spitterRepository.save(spitterDTO);
        return "redirect:/spitter/" + spitter.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showSpiterProfile(Model model, @PathVariable String username) {
        model.addAttribute(spitterRepository.findOne(username));
        return "spitter";
    }

}
