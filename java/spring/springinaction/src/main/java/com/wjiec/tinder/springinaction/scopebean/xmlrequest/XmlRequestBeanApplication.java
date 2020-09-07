package com.wjiec.tinder.springinaction.scopebean.xmlrequest;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.Notepad;
import com.wjiec.tinder.springinaction.scopebean.prototypebean.pencil.Pencil;
import com.wjiec.tinder.springinaction.scopebean.requestbean.WebRequestBeanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@RestController
@ImportResource("classpath:/scopebean/request-bean.xml")
public class XmlRequestBeanApplication {

    private Notepad notepad;
    private Pencil pencil;

    @Autowired
    public XmlRequestBeanApplication(Notepad notepad, Pencil pencil) {
        this.notepad = notepad;
        this.pencil = pencil;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(XmlRequestBeanApplication.class);
        application.setDefaultProperties(new HashMap<>() {{ put("server.port", "18898"); }});
        application.run(args);
    }

    @RequestMapping("/draw")
    public void draw() {
        notepad.print();
    }

    @RequestMapping("/pencil")
    public void pencil() {
        System.out.println(pencil);
    }

}
