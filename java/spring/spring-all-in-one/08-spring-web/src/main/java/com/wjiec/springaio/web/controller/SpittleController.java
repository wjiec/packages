package com.wjiec.springaio.web.controller;

import com.wjiec.springaio.web.repository.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpittleController {

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository repository) {
        this.spittleRepository = repository;
    }

    @RequestMapping(value = "/spittles", method = RequestMethod.GET)
    public String spittles(Model model,
                           @RequestParam(value = "count", defaultValue = "20") long count,
                           @RequestParam(value = "skip", defaultValue = "0") long skip) {
        model.addAttribute("spittleList", spittleRepository.findSpittles(count, skip));
        return "spittles";
    }

    @RequestMapping(value = "/spittle/{spittleId}", method = RequestMethod.GET)
    public String spittle(Model model, @PathVariable("spittleId") long spittleId) {
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

}
