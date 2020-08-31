package com.wjiec.tinder.springinaction.wiring.model.food;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/wiring/xml-wiring.xml")
public class FoodConfig {
}
