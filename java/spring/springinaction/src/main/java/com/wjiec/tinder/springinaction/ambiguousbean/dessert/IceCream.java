package com.wjiec.tinder.springinaction.ambiguousbean.dessert;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class IceCream implements Dessert {
}
