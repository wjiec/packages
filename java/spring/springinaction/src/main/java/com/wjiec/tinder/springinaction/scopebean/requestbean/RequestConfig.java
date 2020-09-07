package com.wjiec.tinder.springinaction.scopebean.requestbean;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.Notepad;
import com.wjiec.tinder.springinaction.scopebean.prototypebean.pencil.Crayon;
import com.wjiec.tinder.springinaction.scopebean.prototypebean.pencil.Pencil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class RequestConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Notepad notepad() {
        return new Notepad(pencil());
    }

    @Bean
    public Pencil pencil() {
        return new Crayon();
    }

}
