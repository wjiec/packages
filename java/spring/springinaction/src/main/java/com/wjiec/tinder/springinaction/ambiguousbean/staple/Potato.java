package com.wjiec.tinder.springinaction.ambiguousbean.staple;

import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Hot;
import com.wjiec.tinder.springinaction.ambiguousbean.annotation.Soft;
import org.springframework.stereotype.Component;

@Component
@Hot
@Soft
public class Potato implements Staple {
}
