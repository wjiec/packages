package com.wjiec.tinder.springinaction.conditional.magic;

import com.wjiec.tinder.springinaction.wiring.model.media.CompactDisc;
import com.wjiec.tinder.springinaction.wiring.model.media.SgtPeppers;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:/conditional/environment.properties")
public class MagicConfiguration {

    @Bean
    @Conditional(MagicCondition.class)
    @HasEnvironment({"conditional.foo","conditional.bar"})
    public CompactDisc compactDisc() {
        return new SgtPeppers();
    }

    @Bean
    @Conditional(MagicCondition.class)
    @HasEnvironment("conditional.baz")
    public CompactDisc bCompactDisc() {
        return () -> System.out.println("anonymous cd");
    }

}
