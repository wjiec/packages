package com.wjiec.tinder.springinaction.ambiguousbean.staple;

import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Crispy;
import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Hot;
import org.springframework.stereotype.Component;

@Component
@Crispy
@Hot
public class Rice implements Staple {
}
