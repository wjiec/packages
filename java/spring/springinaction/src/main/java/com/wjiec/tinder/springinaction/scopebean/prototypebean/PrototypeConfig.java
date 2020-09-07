package com.wjiec.tinder.springinaction.scopebean.prototypebean;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.pencil.Pencil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class PrototypeConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
    public Integer randomNumber(Pencil pencil) {
        System.out.println("PrototypeConfig.randomNumber: " + pencil);
        return (int) (Math.random() * Integer.MAX_VALUE);
    }

}
