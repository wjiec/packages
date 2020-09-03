package com.wjiec.tinder.springinaction.conditional.magic;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

public class MagicCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (context.getEnvironment() != null) {
            MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(HasEnvironment.class.getName());
            if (attrs != null) {
                for (Object attr : attrs.get("value")) {
                    for (String env : (String[]) attr) {
                        if (context.getEnvironment().containsProperty(env)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
