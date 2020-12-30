package com.wjiec.springaio.boot.controller;

import com.wjiec.springaio.boot.model.Contact;
import com.wjiec.springaio.boot.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());

        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addContact(Contact contact) {
        contactRepository.save(contact);

        return "redirect:/";
    }

}
