package com.wjiec.tinder.springinaction.scopebean.requestbean;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.Notepad;
import com.wjiec.tinder.springinaction.scopebean.prototypebean.pencil.Pencil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class WebRequestBeanApplication {

    private Notepad notepad;
    private Pencil pencil;

    @Autowired
    public WebRequestBeanApplication(Notepad notepad, Pencil pencil) {
        this.notepad = notepad;
        this.pencil = pencil;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebRequestBeanApplication.class);
        application.setDefaultProperties(new HashMap<>() {{ put("server.port", "18898"); }});
        application.run(args);
    }

    @RequestMapping("/draw")
    public void draw() {
        System.out.println(notepad);
    }

    @RequestMapping("/pencil")
    public void pencil() {
        System.out.println(pencil);
    }

}
