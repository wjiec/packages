package com.wjiec.tinder.springinaction.scopebean.prototypebean;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.pencil.Pencil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
public class Notepad {

    private Pencil pencil;

    @Autowired
    public Notepad(Pencil pencil) {
        this.pencil = pencil;
    }

    public void print() {
        System.out.println(pencil);
    }

}
