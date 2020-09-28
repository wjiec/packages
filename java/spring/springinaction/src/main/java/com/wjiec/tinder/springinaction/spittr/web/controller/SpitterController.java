package com.wjiec.tinder.springinaction.spittr.web.controller;

import com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO;
import com.wjiec.tinder.springinaction.spittr.exception.SpitterNotFoundException;
import com.wjiec.tinder.springinaction.spittr.model.Spitter;
import com.wjiec.tinder.springinaction.spittr.repository.SpitterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;

@Slf4j
@Controller
@RequestMapping("/spitter")
public class SpitterController {

    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository repository) {
        this.spitterRepository = repository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("spitterDTO", new SpitterDTO());
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration(@Validated SpitterDTO spitterDTO, BindingResult result,
                                      @RequestPart("avatar") Part part, RedirectAttributes model) {
        if (result.hasErrors()) {
            return "registerForm";
        }

        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println(part.getName());
        System.out.println(part.getContentType());
        System.out.println(part.getSize());
        System.out.println(part.getSubmittedFileName());

        Spitter spitter = spitterRepository.save(spitterDTO);
        model.addAttribute("username", spitter.getUsername());
        model.addAttribute("avatar", part.getSubmittedFileName());
        model.addFlashAttribute("spitter", spitter);

        return "redirect:/spitter/{username}";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showSpiterProfile(Model model, @PathVariable String username) {
        if (!model.containsAttribute("spitter")) {
            Spitter spitter = spitterRepository.findOne(username);
            if (spitter == null) {
                throw new SpitterNotFoundException();
            }
            model.addAttribute(spitter);
        } else {
            log.info(model.getAttribute("spitter").toString());
        }

        return "spitter";
    }

    @ExceptionHandler(SpitterNotFoundException.class)
    public String handleSpitterNotFound() {
        log.info("SpitterController.handleSpitterNotFound");
        return "error/spitterNotFound";
    }

}
