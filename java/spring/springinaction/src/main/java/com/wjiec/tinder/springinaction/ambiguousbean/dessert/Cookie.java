package com.wjiec.tinder.springinaction.ambiguousbean.dessert;

import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Crispy;
import org.springframework.stereotype.Component;

@Component
@Crispy
public class Cookie implements Dessert {
}
