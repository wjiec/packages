package com.wjiec.learn.shop.controller;

import com.wjiec.learn.shop.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/random")
    public User randomUser() {
        return User.builder()
            .id(1)
            .name("jayson")
            .age(23)
            .email("jayson@example.com")
            .build();
    }

}
