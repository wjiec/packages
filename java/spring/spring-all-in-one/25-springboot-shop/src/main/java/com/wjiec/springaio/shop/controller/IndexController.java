package com.wjiec.springaio.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Slf4j
@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("principal", principal);
        }

        return "index/home";
    }

}
