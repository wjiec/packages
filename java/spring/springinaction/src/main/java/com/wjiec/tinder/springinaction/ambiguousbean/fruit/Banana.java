package com.wjiec.tinder.springinaction.ambiguousbean.fruit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("banana")
public class Banana implements Fruit {
}
