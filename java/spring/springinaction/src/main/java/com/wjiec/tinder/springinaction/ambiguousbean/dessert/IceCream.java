package com.wjiec.tinder.springinaction.ambiguousbean.dessert;

import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Cold;
import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Cream;
import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Soft;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Cold
@Cream
@Soft
public class IceCream implements Dessert {
}
